package co.bugfreak.results;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.HttpURLConnection;

import co.bugfreak.framework.sequential.ResultCompletedArgs;
import co.bugfreak.framework.sequential.ResultCompletedListener;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExecuteRequestResultTests extends TestCase {

  public void testRunAlwaysExecutesRequest() {
    HttpURLConnection conn = mock(HttpURLConnection.class);
    ExecuteRequestResult request = new ExecuteRequestResult(conn);

    request.run();

    try {
      verify(conn).connect();
      verify(conn).disconnect();
    } catch (IOException e) {
      assertFalse("Should not throw", true);
    }
  }

  public void testRunAlwaysCallsOnCompleteWithCanceledFalse() throws Throwable{
    HttpURLConnection conn = mock(HttpURLConnection.class);
    ResultCompletedListener mockListener = mock(ResultCompletedListener.class);
    ExecuteRequestResult request = new ExecuteRequestResult(conn);
    request.addCompleteListener(mockListener);

    request.run();

    ArgumentCaptor<ResultCompletedArgs> argument = ArgumentCaptor.forClass(ResultCompletedArgs.class);
    verify(mockListener).handle(eq(request), argument.capture());
    assertNull(argument.getValue().getError());
    assertFalse(argument.getValue().wasCancelled());
  }
}
