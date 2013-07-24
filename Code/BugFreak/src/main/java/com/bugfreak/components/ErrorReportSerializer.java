package com.bugfreak.components;

import com.bugfreak.ErrorReport;

public interface ErrorReportSerializer {

  String getContentType();

  String serialize(ErrorReport report);
}
