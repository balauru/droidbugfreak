package co.bugfreak;

public interface ReportingService {
  void beginReport(Throwable throwable);

  void beginReport(Throwable throwable, ReportCompletedCallback reportCompletedCallback);
}
