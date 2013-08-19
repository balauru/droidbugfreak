package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.Result;
import co.bugfreak.framework.SequentialResult;
import co.bugfreak.framework.yieldreturn.Generator;
import co.bugfreak.results.BuildReportRequestResult;
import co.bugfreak.results.ExecuteRequestResult;

public class RemoteErrorReportStorage implements ErrorReportStorage {

  private final ReportRequestBuilder reportRequestBuilder;

  public RemoteErrorReportStorage() {
    reportRequestBuilder = GlobalConfig.getServiceLocator().getService(ReportRequestBuilder.class);
  }

  @Override
  public void saveAsync(final ErrorReport errorReport, final SaveReportCompletedCallback callback) throws Throwable {
    new SequentialResult(new Generator<Result>() {
      @Override
      protected void run() {
        BuildReportRequestResult buildRequest = new BuildReportRequestResult(reportRequestBuilder, errorReport);
        yield(buildRequest);

        ExecuteRequestResult executeRequest = new ExecuteRequestResult(buildRequest.getResult());
        yield(executeRequest);

        callback.onSaveReportCompleted(RemoteErrorReportStorage.this, new ErrorReportSaveCompletedArgs(true));
      }
    }).execute(new ExecutionContext());
  }
}
