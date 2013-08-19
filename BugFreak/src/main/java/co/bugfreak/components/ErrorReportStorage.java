package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.Result;

public interface ErrorReportStorage {
  void saveAsync(ErrorReport errorReport, SaveReportCompletedCallback callback) throws Throwable;
}
