package co.bugfreak;

public interface ReportCompletedCallback {
  void onCompleted(Throwable throwable, boolean reported);
}
