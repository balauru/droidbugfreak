package co.bugfreak.components;

import co.bugfreak.ErrorReport;

public class LocalErrorReportStorage implements ErrorReportStorage {
  @Override
  public boolean save(ErrorReport errorReport) throws Throwable {
    return true;
  }
}
