package co.bugfreak.results;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import co.bugfreak.components.ErrorReportSaveCompletedArgs;
import co.bugfreak.components.ErrorReportStorage;
import co.bugfreak.components.SaveReportCompletedCallback;
import co.bugfreak.framework.ExecutionContext;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotifyReportSaveCompletedResultTests extends TestCase {

  public void testExecuteAlwaysCallsOnSaveReportCompleted() throws Throwable {
    ErrorReportStorage mockStorage = mock(ErrorReportStorage.class);
    SaveReportCompletedCallback mockCallback = mock(SaveReportCompletedCallback.class);
    NotifyReportSaveCompletedResult subject = new NotifyReportSaveCompletedResult(mockStorage, mockCallback);

    subject.execute(new ExecutionContext());

    ArgumentCaptor<ErrorReportSaveCompletedArgs> argument = ArgumentCaptor.forClass(ErrorReportSaveCompletedArgs.class);
    verify(mockCallback).onSaveReportCompleted(eq(mockStorage), argument.capture());
    assertTrue(argument.getValue().isSuccess());
  }
}
