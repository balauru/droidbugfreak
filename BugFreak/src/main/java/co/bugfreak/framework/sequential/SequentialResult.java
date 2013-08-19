package co.bugfreak.framework.sequential;

import java.util.Iterator;

import co.bugfreak.results.ResultBase;

public class SequentialResult extends ResultBase implements Result, ResultCompletedListener {

  private Iterator<Result> iterator;
  private ExecutionContext context;

  public SequentialResult(Iterator<Result> iterator) {
    this.iterator = iterator;
  }

  public SequentialResult(Iterable<Result> iterable) {
    this(iterable.iterator());
  }

  @Override
  public void execute(ExecutionContext context) {
    this.context = context;
    childCompleted(null, new ResultCompletedArgs());
  }

  void childCompleted(Result previous, ResultCompletedArgs args) {
    if (previous != null) {
      previous.removeCompleteListener(this);
    }

    if (args.getError() != null || args.wasCancelled()) {
      onCompleted(args.getError(), args.wasCancelled());
      return;
    }

    boolean moveNextSucceeded;
    Result nextResult = null;

    try {
      if (moveNextSucceeded = iterator.hasNext()) {
        nextResult = iterator.next();
      }
    } catch (Throwable ex) {
      onCompleted(ex, false);
      return;
    }

    if (moveNextSucceeded) {
      try {
        nextResult.addCompleteListener(this);
        nextResult.execute(context);
      } catch (Throwable ex) {
        onCompleted(ex, false);
      }
    } else {
      onCompleted(null, false);
    }
  }

  @Override
  public void onCompleted(Result result, ResultCompletedArgs args) {
    childCompleted(result, args);
  }
}
