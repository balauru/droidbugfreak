package co.bugfreak.components;

import co.bugfreak.ErrorReport;

public interface ErrorReportStorage {
  void saveAsync(ErrorReport errorReport, SaveReportCompletedCallback callback) throws Throwable;
}
