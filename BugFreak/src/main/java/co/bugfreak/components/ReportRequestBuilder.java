package co.bugfreak.components;

import co.bugfreak.ErrorReport;

import java.net.HttpURLConnection;

public interface ReportRequestBuilder {
  HttpURLConnection build(ErrorReport report);
}
