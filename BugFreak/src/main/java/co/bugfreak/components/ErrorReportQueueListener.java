package co.bugfreak.components;

import co.bugfreak.framework.Disposable;

public interface ErrorReportQueueListener extends Disposable {
  void listen(ErrorReportQueue queue);
}
