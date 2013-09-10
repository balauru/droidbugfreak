package co.bugfreak.results;

import java.util.ArrayList;
import java.util.List;

import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.ResultCompletedListener;
import co.bugfreak.framework.sequential.ResultCompletedArgs;

public abstract class ResultBase implements Result {

  private List<ResultCompletedListener> completeListeners;

  protected ResultBase() {
    completeListeners = new ArrayList<ResultCompletedListener>();
  }

  @Override
  public void addCompleteListener(ResultCompletedListener handler) {
    completeListeners.add(handler);
  }

  @Override
  public void removeCompleteListener(ResultCompletedListener handler) {
    completeListeners.remove(handler);
  }

  protected void onCompleted(Throwable error, boolean wasCancelled) {
    for (ResultCompletedListener listener : completeListeners) {
      listener.handle(this, new ResultCompletedArgs(wasCancelled, error));
    }
  }
}
