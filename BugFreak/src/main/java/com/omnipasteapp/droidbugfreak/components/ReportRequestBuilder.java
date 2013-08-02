package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

import java.net.HttpURLConnection;

public interface ReportRequestBuilder {
  HttpURLConnection build(ErrorReport report);
}
