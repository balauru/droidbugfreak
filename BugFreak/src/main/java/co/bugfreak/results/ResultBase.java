package co.bugfreak.results;

import java.util.ArrayList;
import java.util.List;

import co.bugfreak.framework.Result;
import co.bugfreak.framework.ResultCompletedListener;
import co.bugfreak.framework.ResultCompletedArgs;

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
      listener.onCompleted(this, new ResultCompletedArgs(wasCancelled, error));
    }
  }
}
