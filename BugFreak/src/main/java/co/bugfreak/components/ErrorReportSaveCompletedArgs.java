package co.bugfreak.components;

public class ErrorReportSaveCompletedArgs {
  private final boolean success;

  public ErrorReportSaveCompletedArgs(boolean success) {
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }
}
