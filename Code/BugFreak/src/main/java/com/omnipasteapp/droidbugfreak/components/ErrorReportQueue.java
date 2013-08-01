package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.collections.ObservableList;
import com.omnipasteapp.droidbugfreak.framework.EventHandler;

public interface ErrorReportQueue {
  void addItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void removeItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void enqueue(ErrorReport errorReport);

  ErrorReport dequeue();
}
