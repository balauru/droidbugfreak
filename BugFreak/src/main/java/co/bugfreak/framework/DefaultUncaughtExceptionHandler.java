package co.bugfreak.framework;

import co.bugfreak.BugFreak;
import co.bugfreak.ReportCompletedCallback;

public class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

  public static DefaultUncaughtExceptionHandler create() {
    Thread.UncaughtExceptionHandler originalHandler = Thread.getDefaultUncaughtExceptionHandler();

    return new DefaultUncaughtExceptionHandler(originalHandler);
  }

  private final Thread.UncaughtExceptionHandler defaultHandler;

  private DefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler defaultHandler) {
    this.defaultHandler = defaultHandler;
  }

  @Override
  public void uncaughtException(final Thread thread, final Throwable ex) {
    BugFreak.beginReport(ex, new ReportCompletedCallback() {
      @Override
      public void onCompleted(Throwable throwable, boolean reported) {
        defaultHandler.uncaughtException(thread, ex);
      }
    });
  }
}
