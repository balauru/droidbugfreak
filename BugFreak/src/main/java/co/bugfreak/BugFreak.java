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

  public static ReportingService getInstance() {
    return instance;
  }

  public static void dispose() {
    errorHandler.dispose();

    instance = null;
  }

  public static void hook(String apiKey, String token, Application app) {
    GlobalConfig.setApiKey(apiKey);
    GlobalConfig.setToken(token);

    init();

    GlobalConfig.addDataProvider(new AndroidProvider(app));

    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, final Throwable ex) {
        instance.beginReport(ex);
      }
    });
  }

  @Override
  public void beginReport(Throwable throwable) {
    beginReport(throwable, null);
  }

  @Override
  public void beginReport(Throwable throwable, ReportCompletedCallback reportCompletedCallback) {
    errorHandler.handle(throwable, reportCompletedCallback);
  }
}