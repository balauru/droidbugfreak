package com.bugfreak.components;

import com.bugfreak.framework.Disposable;

public interface ErrorReportQueueListener extends Disposable {
  void listen(ErrorReportQueue queue);
}
