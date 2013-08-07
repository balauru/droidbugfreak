package co.bugfreak.components;

import co.bugfreak.ErrorReport;

public interface ErrorReportStorage {
  boolean save(ErrorReport errorReport) throws Throwable;
}
