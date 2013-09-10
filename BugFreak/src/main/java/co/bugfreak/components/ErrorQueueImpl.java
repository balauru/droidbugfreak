package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.collections.ObservableList;

public class ErrorQueueImpl extends ObservableList<PendingReport> implements ErrorQueue {

  @Override
  public void enqueue(PendingReport errorReport) {
    add(errorReport);
  }

  @Override
  public PendingReport dequeue() {
    PendingReport result = null;

    if (size() > 0) {
      result = remove(0);
    }

    return result;
  }
}
