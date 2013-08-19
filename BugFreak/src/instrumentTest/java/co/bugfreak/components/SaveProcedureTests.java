package co.bugfreak.components;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.List;

import co.bugfreak.ErrorReport;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.results.BuildReportRequestResult;
import co.bugfreak.results.ExecuteRequestResult;
import co.bugfreak.utils.Array;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaveProcedureTests extends TestCase {
  public void testRunAlwaysBuildReportRequestResultAndExecuteReportRequest() {
    RemoteErrorReportStorage storage = new RemoteErrorReportStorage();
    ErrorReport errorReport = new ErrorReport();
    SaveReportCompletedCallback callback = mock(SaveReportCompletedCallback.class);
    RemoteErrorReportStorage.PostToServiceProcedure procedure = storage.new PostToServiceProcedure(errorReport, callback);

    Iterator<Result> result = procedure.iterator();

    List<Result> results = Array.toList(result);
    assertTrue(results.get(0) instanceof BuildReportRequestResult);
    assertTrue(results.get(1) instanceof ExecuteRequestResult);
  }

  public void testRunAlwaysCallsCallbackOnSaveReportCompleted() {
    RemoteErrorReportStorage storage = new RemoteErrorReportStorage();
    ErrorReport errorReport = new ErrorReport();
    SaveReportCompletedCallback callback = mock(SaveReportCompletedCallback.class);
    RemoteErrorReportStorage.PostToServiceProcedure procedure = storage.new PostToServiceProcedure(errorReport, callback);

    Iterator<Result> result = procedure.iterator();

    Array.toList(result);
    verify(callback).onSaveReportCompleted(eq(storage), any(ErrorReportSaveCompletedArgs.class));
  }
}
