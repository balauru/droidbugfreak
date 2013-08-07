package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.collections.ObservableList;
import co.bugfreak.framework.EventHandler;

public interface ErrorReportQueue {
  void addItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void removeItemAddedListener(EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> handler);

  void enqueue(ErrorReport errorReport);

  ErrorReport dequeue();
}
