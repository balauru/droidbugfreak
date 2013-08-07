package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.Disposable;

public interface ErrorReportHandler extends Disposable {
  void handle(ErrorReport report);
}
