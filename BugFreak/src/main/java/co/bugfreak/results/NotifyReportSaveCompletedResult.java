package co.bugfreak.results;

import co.bugfreak.components.ErrorReportSaveCompletedArgs;
import co.bugfreak.components.ErrorReportStorage;
import co.bugfreak.components.SaveReportCompletedCallback;
import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.Result;

public class NotifyReportSaveCompletedResult extends ResultBase implements Result {

  private final ErrorReportStorage storage;
  private final SaveReportCompletedCallback callback;

  public NotifyReportSaveCompletedResult(ErrorReportStorage storage, SaveReportCompletedCallback callback) {
    this.storage = storage;
    this.callback = callback;
  }

  @Override
  public void execute(ExecutionContext context) throws Throwable {
    callback.onSaveReportCompleted(storage, new ErrorReportSaveCompletedArgs(true));
  }
}
