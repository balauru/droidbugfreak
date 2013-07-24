package com.bugfreak;

import com.bugfreak.components.ErrorReportHandler;
import com.bugfreak.components.ErrorReportQueue;
import com.bugfreak.components.ErrorReportQueueListener;

public class AgileReporter implements ReportingService {

  static ReportingService instance;

  public static void init() {
    Initializer.initialize();
  }

  public static ReportingService getInstance() {
    return instance;
  }

  AgileReporter() {
  }

  @Override
  public void beginReport(Throwable exc) {
    ErrorReport errorReport = createReport(exc);
    queue(errorReport);
  }

  private ErrorReport createReport(Throwable exc) {
    return ErrorReport.fromException(exc);
  }

  private void queue(ErrorReport errorReport) {
    ErrorReportQueue errorReportQueue = GlobalConfig.getServiceLocator().getService(ErrorReportQueue.class);

    errorReportQueue.enqueue(errorReport);
  }

  public static void dispose() {
    GlobalConfig.getServiceLocator().getService(ErrorReportQueueListener.class).dispose();
    GlobalConfig.getServiceLocator().getService(ErrorReportHandler.class).dispose();

    instance = null;
  }
  
  public static void hook() {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread thread, Throwable ex) {
        AgileReporter.getInstance().beginReport(ex);
      }
    });
  }
}
