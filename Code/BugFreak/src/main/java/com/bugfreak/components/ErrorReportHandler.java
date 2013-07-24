package com.bugfreak.components;

import com.bugfreak.ErrorReport;
import com.bugfreak.framework.Disposable;

public interface ErrorReportHandler extends Disposable {
  void handle(ErrorReport report);
}
