package co.bugfreak.components;

import co.bugfreak.ErrorReport;

public interface ErrorReportSerializer {

  String getContentType();

  String serialize(ErrorReport report);
}
