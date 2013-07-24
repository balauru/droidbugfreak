package com.bugfreak.components;

import com.bugfreak.ErrorReport;
import com.bugfreak.collections.ObservableList;
import com.bugfreak.framework.EventHandler;

public interface ErrorReportQueue {
  void addItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void removeItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void enqueue(ErrorReport errorReport);

  ErrorReport dequeue();
}
