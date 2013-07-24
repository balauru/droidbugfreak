package com.bugfreak.components;

import com.bugfreak.ErrorReport;

import java.net.HttpURLConnection;

public interface ReportRequestBuilder {
  HttpURLConnection build(ErrorReport report);
}
