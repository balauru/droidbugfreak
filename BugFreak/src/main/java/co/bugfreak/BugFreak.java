package co.bugfreak;

import android.app.Application;

import co.bugfreak.components.AndroidProvider;
import co.bugfreak.components.ErrorHandler;

public class BugFreak implements ReportingService {

  static ReportingService instance;
  private static ErrorHandler errorHandler;

  BugFreak() {
  }

  public static void init() {
    Initializer.initialize();

    errorHandler = GlobalConfig.getServiceLocator().getService(ErrorHandler.class);
  }

  public static void hook(String apiKey, String token, Application app) {
    GlobalConfig.setApiKey(apiKey);
    GlobalConfig.setToken(token);

    init();

    GlobalConfig.addDataProvider(new AndroidProvider(app));

    RegisterExceptionHandler();
  }

  private static void RegisterExceptionHandler() {
    final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(final Thread thread, final Throwable ex) {
        beginReport(ex, new ReportCompletedCallback() {
          @Override
          public void onCompleted(Throwable throwable, boolean reported) {
            defaultHandler.uncaughtException(thread, ex);
          }
        });
      }
    });
  }

  public static void beginReport(Throwable throwable) {
    beginReport(throwable, null);
  }

  public static void beginReport(Throwable throwable, ReportCompletedCallback reportCompletedCallback) {
    errorHandler.handle(throwable, reportCompletedCallback);
  }
}