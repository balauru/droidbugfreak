package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.Result;
import co.bugfreak.framework.SequentialResult;
import co.bugfreak.results.BuildReportRequestResult;
import co.bugfreak.results.ExecuteRequestResult;
import co.bugfreak.results.NotifyReportSaveCompletedResult;
import co.bugfreak.utils.Array;

import java.net.HttpURLConnection;

public class RemoteErrorReportStorage implements ErrorReportStorage {

  private final ReportRequestBuilder reportRequestBuilder;

  public RemoteErrorReportStorage() {
    reportRequestBuilder = GlobalConfig.getServiceLocator().getService(ReportRequestBuilder.class);
  }

  @Override
  public void saveAsync(final ErrorReport errorReport, final SaveReportCompletedCallback callback) throws Throwable {
    BuildReportRequestResult buildRequest = new BuildReportRequestResult(reportRequestBuilder, errorReport);
    ExecuteRequestResult executeRequest = new ExecuteRequestResult(buildRequest);
    NotifyReportSaveCompletedResult saveCompleted = new NotifyReportSaveCompletedResult(this, callback);

    new SequentialResult(Array.of(Result.class,
        buildRequest,
        executeRequest,
        saveCompleted)).execute(new ExecutionContext());
  }
}
