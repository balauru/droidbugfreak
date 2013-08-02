package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

public class LocalErrorReportStorage implements ErrorReportStorage {
  @Override
  public boolean save(ErrorReport errorReport) throws Throwable {
    return true;
  }
}
