package com.bugfreak.components;

import com.bugfreak.ErrorReport;

public interface ErrorReportStorage {
  boolean save(ErrorReport errorReport) throws Throwable;
}
