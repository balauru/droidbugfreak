package co.bugfreak.components;

import co.bugfreak.ReportCompletedCallback;

public class PendingReport {
  private Throwable throwable;
  private ReportCompletedCallback reportCompletedCallback;

  public PendingReport(Throwable throwable, ReportCompletedCallback reportCompletedCallback) {
    this.throwable = throwable;
    this.reportCompletedCallback = reportCompletedCallback;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public ReportCompletedCallback getReportCompletedCallback() {
    return reportCompletedCallback;
  }
}
