package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.Disposable;
import co.bugfreak.framework.Result;

public interface ErrorReportHandler extends Disposable {
  Iterable<Result> handle(ErrorReport report);
}
