package co.bugfreak;

import android.app.Application;

import co.bugfreak.components.AndroidProvider;
import co.bugfreak.components.ErrorHandler;
import co.bugfreak.framework.DefaultUncaughtExceptionHandler;

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
    DefaultUncaughtExceptionHandler exceptionHandler = DefaultUncaughtExceptionHandler.create();

    Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
  }

  public static void beginReport(Throwable throwable) {
    beginReport(throwable, null);
  }

  public static void beginReport(Throwable throwable, ReportCompletedCallback reportCompletedCallback) {
    errorHandler.handle(throwable, reportCompletedCallback);
  }
}