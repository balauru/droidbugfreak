package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.framework.Disposable;

public interface ErrorReportHandler extends Disposable {
  void handle(ErrorReport report);
}
