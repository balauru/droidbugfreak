package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.Disposable;
import co.bugfreak.framework.sequential.Result;

public interface ErrorReportHandler extends Disposable {
  void handle(ErrorReport report);
}
