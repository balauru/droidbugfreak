package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

import junit.framework.TestCase;

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
