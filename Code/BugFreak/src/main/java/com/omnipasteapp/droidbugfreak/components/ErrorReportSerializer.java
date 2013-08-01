package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

public interface ErrorReportSerializer {

  String getContentType();

  String serialize(ErrorReport report);
}
