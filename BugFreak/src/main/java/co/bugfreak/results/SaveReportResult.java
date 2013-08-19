package co.bugfreak.results;

import co.bugfreak.ErrorReport;
import co.bugfreak.components.ErrorReportSaveCompletedArgs;
import co.bugfreak.components.ErrorReportStorage;
import co.bugfreak.components.SaveReportCompletedCallback;
import co.bugfreak.framework.sequential.ExecutionContext;
import co.bugfreak.framework.sequential.Result;

public class SaveReportResult extends ResultBase implements Result, SaveReportCompletedCallback {

  private final ErrorReportStorage storage;
  private final ErrorReport report;

  public SaveReportResult(ErrorReportStorage storage, ErrorReport report) {
    this.storage = storage;
    this.report = report;
  }

  @Override
  public void execute(ExecutionContext context) throws Throwable {
    storage.saveAsync(report, this);
  }

  @Override
  public void onSaveReportCompleted(ErrorReportStorage storage, ErrorReportSaveCompletedArgs args) {
    onCompleted(null, args.isSuccess());
  }
}
