package co.bugfreak.results;

import junit.framework.TestCase;

import co.bugfreak.ErrorReport;
import co.bugfreak.components.ErrorReportStorage;
import co.bugfreak.framework.sequential.ExecutionContext;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaveReportResultTests extends TestCase {

  public void testExecuteAlwaysCallsStorageSaveAsync() throws Throwable {
    ErrorReport errorReport = new ErrorReport();
    ErrorReportStorage storage = mock(ErrorReportStorage.class);
    SaveReportResult subject = new SaveReportResult(storage, errorReport);

    subject.execute(new ExecutionContext());

    verify(storage).saveAsync(eq(errorReport), eq(subject));
  }
}
