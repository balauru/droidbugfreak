package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;

import java.net.HttpURLConnection;

public class RemoteErrorReportStorage implements ErrorReportStorage {

  private final ReportRequestBuilder reportRequestBuilder;

  public RemoteErrorReportStorage() {
    reportRequestBuilder = GlobalConfig.getServiceLocator().getService(ReportRequestBuilder.class);
  }

  @Override
  public boolean save(ErrorReport errorReport) throws Throwable {
    HttpURLConnection conn = reportRequestBuilder.build(errorReport);

    conn.connect();
    conn.getResponseCode();
    conn.disconnect();

    return true;
  }
}
