package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.collections.ObservableList;

public class ErrorReportQueueImpl extends ObservableList<ErrorReport> implements ErrorReportQueue {

  @Override
  public void enqueue(ErrorReport errorReport) {
    add(errorReport);
  }

  @Override
  public ErrorReport dequeue() {
    ErrorReport result = null;

    if (size() > 0) {
      result = remove(0);
    }

    return result;
  }
}
