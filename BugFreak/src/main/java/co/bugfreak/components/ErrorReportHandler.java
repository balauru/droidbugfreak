package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.Disposable;
import co.bugfreak.framework.sequential.Result;

public interface ErrorReportHandler extends Disposable {
  Iterable<Result> handle(ErrorReport report);
}
