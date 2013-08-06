package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.GlobalConfig;

import junit.framework.TestCase;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class ErrorReportTests extends TestCase {

  public void testFromExceptionAlwaysSetsMessageOnReport() {
    Exception exc = createException();

    ErrorReport result = ErrorReport.fromException(exc);

    assertEquals(exc.getMessage(), result.getMessage());
  }

  public void testFromExceptionAlwaysSetsStackTraceOnReport() {
    Exception exc = createException();

    ErrorReport result = ErrorReport.fromException(exc);

    assertNotNull(result.getStackTrace());
  }

  public void testAddDataAlwaysAddsKeyAndValueToAdditionalData() {
    Exception exc = createException();
    ErrorReport result = ErrorReport.fromException(exc);

    result.addData("key", "value");

    assertEquals("value", result.getAdditionalData().get("key"));
  }

  public void testFromExceptionAlwaysAddsDataFromGlobalConfigDataProviders() {
    ErrorReportDataProvider provider = mock(ErrorReportDataProvider.class);
    HashMap data = new HashMap<String, String>();
    data.put("key", "value");
    when(provider.getData()).thenReturn(data);
    GlobalConfig.addDataProvider(provider);

    ErrorReport result = ErrorReport.fromException(createException());

    assertEquals("value", result.getAdditionalData().get("key"));
  }

  private Exception createException() {
    Exception result;

    try {
      throw new Exception("test");
    } catch (Exception exc) {
      result = exc;
    }

    return result;
  }
}
