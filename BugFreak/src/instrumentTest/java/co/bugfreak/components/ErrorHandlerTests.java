package co.bugfreak.components;

import junit.framework.TestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.ReportCompletedCallback;
import co.bugfreak.framework.SimpleServiceLocator;
import co.bugfreak.helpers.MockResult;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class ErrorHandlerTests extends TestCase {
  private ErrorHandler subject;

  @Mock
  private ErrorReportStorage remoteErrorReportStorage;

  @Mock
  private ErrorReportStorage localErrorReportStorage;

  public void setUp() {
    MockitoAnnotations.initMocks(this);

    ErrorQueue errorQueue = new ErrorQueueImpl();

    SimpleServiceLocator serviceLocator = new SimpleServiceLocator();
    serviceLocator.addService(ErrorReportStorage.class, remoteErrorReportStorage);
    serviceLocator.addService(ErrorReportStorage.class, localErrorReportStorage);
    serviceLocator.addService(ErrorQueue.class, errorQueue);

    GlobalConfig.setServiceLocator(serviceLocator);

    subject = new ErrorHandlerImpl();
  }

  public void testHandleAlwaysCallsRemoteErrorReportStorageSave() throws Throwable {
    Throwable throwable = new Throwable();

    subject.handle(throwable, null);

    verify(remoteErrorReportStorage).saveAsync(any(ErrorReport.class), any(SaveReportCompletedCallback.class));
  }
}
