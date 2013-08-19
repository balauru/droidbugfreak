package co.bugfreak.results;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.net.HttpURLConnection;

import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.Result;

public class ExecuteRequestResult extends ResultBase implements Runnable, Result {
  private final BuildReportRequestResult buildResult;

  public ExecuteRequestResult(BuildReportRequestResult buildResult) {
    this.buildResult = buildResult;
  }

  @Override
  public void execute(ExecutionContext context) {
    new Thread(this).start();
  }

  @Override
  public void run() {
    try {
      HttpURLConnection httpURLConnection = buildResult.getResult();

      httpURLConnection.connect();
      int statusCode = httpURLConnection.getResponseCode();
      httpURLConnection.disconnect();

      onCompleted(null, statusCode == HttpStatus.SC_CREATED);
    } catch (IOException e) {
      onCompleted(e, true);
    }
  }
}
