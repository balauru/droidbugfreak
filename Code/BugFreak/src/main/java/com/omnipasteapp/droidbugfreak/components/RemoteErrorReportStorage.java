package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.GlobalConfig;

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

    if (conn != null) {
      conn.disconnect();
    }

    return true;
  }
}
