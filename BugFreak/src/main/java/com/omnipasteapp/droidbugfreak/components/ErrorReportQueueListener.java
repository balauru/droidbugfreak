package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.framework.Disposable;

public interface ErrorReportQueueListener extends Disposable {
  void listen(ErrorReportQueue queue);
}
