package co.bugfreak.framework;

import junit.framework.TestCase;

import co.bugfreak.framework.sequential.ExecutionContext;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.SequentialResult;
import co.bugfreak.results.ResultBase;
import co.bugfreak.utils.Array;

public class SequentialResultTests extends TestCase {

  public void testExecuteCallsExecuteOnAllResults() throws Throwable {
    ChildResult childResult1 = new ChildResult(null, false);
    ChildResult childResult2 = new ChildResult(null, false);
    Result sequentialResult = new SequentialResult(Array.of(Result.class, childResult1, childResult2));

    try {
      sequentialResult.execute(new ExecutionContext());
    } catch (Throwable throwable) {
      assertFalse("Should not throw", true);
    }

    assertTrue(childResult1.wasExecuteCalled);
    assertTrue(childResult2.wasExecuteCalled);
  }

  public class ChildResult extends ResultBase {

    private final Throwable error;
    private final boolean canceled;

    public boolean wasExecuteCalled;

    public ChildResult(Throwable error, boolean canceled) {
      this.error = error;
      this.canceled = canceled;
    }

    @Override
    public void execute(ExecutionContext context) throws Throwable {
      wasExecuteCalled = false;

      onCompleted(error, canceled);

      wasExecuteCalled = true;
    }
  }
}
