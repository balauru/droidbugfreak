package co.bugfreak.framework.sequential;

public class ResultCompletedArgs {
  private Throwable error;
  private boolean canceled;

  public ResultCompletedArgs() {
  }

  public ResultCompletedArgs(boolean wasCanceled) {
    this.canceled = wasCanceled;
  }

  public ResultCompletedArgs(Throwable throwable) {
    this.error = throwable;
  }

  public ResultCompletedArgs(boolean wasCanceled, Throwable throwable) {
    this.canceled = wasCanceled;
    this.error = throwable;
  }

  public Throwable getError() {
    return error;
  }

  public void setError(Throwable error) {
    this.error = error;
  }

  public boolean wasCancelled() {
    return canceled;
  }

  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
}
