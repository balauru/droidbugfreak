package co.bugfreak.framework.sequential;

public class Sequentially {

  public static void execute(Iterable<Result> iterable) {
    new SequentialResult(iterable).execute(new ExecutionContext());
  }

  public static void execute(Iterable<Result> iterable, ResultCompletedListener listener) {
    SequentialResult result = new SequentialResult(iterable);
    result.addCompleteListener(listener);
    result.execute(new ExecutionContext());
  }
}
