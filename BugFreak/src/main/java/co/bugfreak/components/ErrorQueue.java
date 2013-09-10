package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.collections.ObservableList;
import co.bugfreak.framework.EventHandler;

public interface ErrorQueue {
  void addItemAddedListener(EventHandler<ObservableList<PendingReport>.ListChangedEventArgs> handler);

  void removeItemAddedListener(EventHandler<ObservableList<PendingReport>.ListChangedEventArgs> handler);

  void enqueue(PendingReport pendingReport);

  PendingReport dequeue();
}
