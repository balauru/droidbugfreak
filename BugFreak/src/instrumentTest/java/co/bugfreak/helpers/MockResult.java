package co.bugfreak.helpers;

import co.bugfreak.framework.sequential.ExecutionContext;
import co.bugfreak.results.ResultBase;

public class MockResult extends ResultBase {

  private final boolean wasCanceled;

  public MockResult(boolean wasCanceled) {
    this.wasCanceled = wasCanceled;
  }

  @Override
  public void execute(ExecutionContext context) throws Throwable {
    onCompleted(null, wasCanceled);
  }
}
