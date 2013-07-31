package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.GlobalConfig;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReportRequestBuilderImpl implements ReportRequestBuilder {
  public final String ApiKey = "ApiKey";
  public final String TokenKey = "Token";
  public final String HttpMethod = "POST";
  public final String AppNameKey = "AppName";

  @Override
  public HttpURLConnection build(ErrorReport report) {
    HttpURLConnection request = null;
    try {
      request = createConnection();
      setMethod(request);
      sign(request);
      write(report, request);
    } catch (Exception exc) {
      // nothing will go wrong
    }

    return request;
  }

  private void write(ErrorReport report, HttpURLConnection request) throws IOException {
    ErrorReportSerializer serializer = GlobalConfig.getServiceLocator().getService(ErrorReportSerializer.class);

    request.setRequestProperty("Content-Type", serializer.getContentType());

    OutputStream outputStream = request.getOutputStream();
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

    bufferedWriter.write(serializer.serialize(report));

    bufferedWriter.flush();
    bufferedWriter.close();
    outputStreamWriter.close();
  }

  private void sign(HttpURLConnection request) {
    request.setRequestProperty(ApiKey, GlobalConfig.Settings.getApiKey());
    request.setRequestProperty(TokenKey, GlobalConfig.Settings.getToken());
    request.setRequestProperty(AppNameKey, GlobalConfig.Settings.getAppName());
  }

  private HttpURLConnection createConnection() throws Exception {
    URL url = new URL(GlobalConfig.Settings.getServiceEndPoint());
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    return urlConnection;
  }

  public void setMethod(HttpURLConnection conn) throws Exception {
    conn.setRequestMethod(HttpMethod);
  }
}
