package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.Sequentially;
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
    Sequentially.execute(new PostToServiceProcedure(errorReport, callback));
  }

  class PostToServiceProcedure extends Generator<Result> {

    private final ErrorReport errorReport;
    private final SaveReportCompletedCallback callback;

    PostToServiceProcedure(ErrorReport errorReport, SaveReportCompletedCallback callback) {
      this.errorReport = errorReport;
      this.callback = callback;
    }

    @Override
    protected void run() {
      BuildReportRequestResult buildRequest = new BuildReportRequestResult(reportRequestBuilder, errorReport);
      yield(buildRequest);

      ExecuteRequestResult executeRequest = new ExecuteRequestResult(buildRequest.getResult());
      yield(executeRequest);

      callback.onSaveReportCompleted(RemoteErrorReportStorage.this, new ErrorReportSaveCompletedArgs(true));
    }
  }
}
