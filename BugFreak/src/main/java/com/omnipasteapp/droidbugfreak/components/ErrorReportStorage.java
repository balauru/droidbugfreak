package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

public interface ErrorReportStorage {
  boolean save(ErrorReport errorReport) throws Throwable;
}
