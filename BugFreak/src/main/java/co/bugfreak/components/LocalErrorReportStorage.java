package co.bugfreak.components;

import co.bugfreak.ErrorReport;

public class LocalErrorReportStorage implements ErrorReportStorage {
  @Override
  public void saveAsync(ErrorReport errorReport, SaveReportCompletedCallback callback) throws Throwable {
    callback.onSaveReportCompleted(this, new ErrorReportSaveCompletedArgs(true));
  }
}
