package com.bugfreak.components;

import com.bugfreak.ErrorReport;
import com.bugfreak.GlobalConfig;

import java.net.HttpURLConnection;

public class RemoteErrorReportStorage implements ErrorReportStorage {

  private final ReportRequestBuilder reportRequestBuilder;

  public RemoteErrorReportStorage() {
    reportRequestBuilder = GlobalConfig.getServiceLocator().getService(ReportRequestBuilder.class);
  }

  @Override
  public boolean save(ErrorReport errorReport) throws Throwable {
    HttpURLConnection conn = reportRequestBuilder.build(errorReport);

    conn.getInputStream();

    if (conn != null) {
      conn.disconnect();
    }

    return true;
  }
}
