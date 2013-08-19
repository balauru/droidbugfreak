package co.bugfreak.results;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;

import co.bugfreak.framework.sequential.ExecutionContext;
import co.bugfreak.framework.sequential.Result;

public class ExecuteRequestResult extends ResultBase implements Runnable, Result {

  private final HttpURLConnection httpURLConnection;

  public ExecuteRequestResult(HttpURLConnection httpURLConnection) {
    this.httpURLConnection = httpURLConnection;
  }

  @Override
  public void execute(ExecutionContext context) {
    new Thread(this).start();
  }

  @Override
  public void run() {
    try {
      httpURLConnection.connect();
      int statusCode = httpURLConnection.getResponseCode();
      httpURLConnection.disconnect();

      onCompleted(null, statusCode == HttpStatus.SC_CREATED);
    } catch (IOException e) {
      onCompleted(e, true);
    }
  }
}
