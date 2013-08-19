package co.bugfreak.results;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;

import java.io.IOException;
import java.net.HttpURLConnection;

import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.ResultCompletedArgs;
import co.bugfreak.framework.ResultCompletedListener;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

  public void testRunAlwaysCallsOnCompleteWithCanceledTrueWhenResponseCodeIs201() throws Throwable{
    HttpURLConnection conn = mock(HttpURLConnection.class);
    when(conn.getResponseCode()).thenReturn(201);
    ResultCompletedListener mockListener = mock(ResultCompletedListener.class);
    ExecuteRequestResult request = new ExecuteRequestResult(conn);
    request.addCompleteListener(mockListener);

    request.run();

    ArgumentCaptor<ResultCompletedArgs> argument = ArgumentCaptor.forClass(ResultCompletedArgs.class);
    verify(mockListener).onCompleted(eq(request), argument.capture());
    assertNull(argument.getValue().getError());
    assertTrue(argument.getValue().wasCancelled());
  }
}
