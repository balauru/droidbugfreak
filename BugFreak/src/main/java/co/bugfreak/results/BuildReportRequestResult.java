package co.bugfreak.results;

import java.net.HttpURLConnection;

import co.bugfreak.ErrorReport;
import co.bugfreak.components.ReportRequestBuilder;
import co.bugfreak.framework.sequential.ExecutionContext;

public class BuildReportRequestResult extends ResultBase implements Runnable {
  private final ReportRequestBuilder reportRequestBuilder;
  private final ErrorReport errorReport;
  private HttpURLConnection result;

  public BuildReportRequestResult(ReportRequestBuilder reportRequestBuilder, ErrorReport errorReport) {
    this.reportRequestBuilder = reportRequestBuilder;
    this.errorReport = errorReport;
  }

  @Override
  public void execute(ExecutionContext context) throws Throwable {
    new Thread(this).start();
  }

  @Override
  public void run() {
    result = reportRequestBuilder.build(errorReport);

    onCompleted(null, false);
  }

  public HttpURLConnection getResult() {
    return result;
  }
}
