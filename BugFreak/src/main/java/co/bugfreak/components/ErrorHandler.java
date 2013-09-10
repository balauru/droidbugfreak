package co.bugfreak.components;

import co.bugfreak.ReportCompletedCallback;
import co.bugfreak.framework.Disposable;

public interface ErrorHandler extends Disposable {
  void handle(Throwable throwable, ReportCompletedCallback reportCompletedCallback);
}
