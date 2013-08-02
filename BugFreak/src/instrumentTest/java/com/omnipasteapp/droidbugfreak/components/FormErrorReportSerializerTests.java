package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;

import junit.framework.TestCase;

public class FormErrorReportSerializerTests extends TestCase {
    private FormErrorReportSerializer subject;

    protected void setUp() {
        subject = new FormErrorReportSerializer();
    }

    public void testSerializeAlwaysFormatsStringProperties() {
        ErrorReport errorReport = new ErrorReport();
        errorReport.setMessage("test message");
        errorReport.setStackTrace("stack trace");

        String result = subject.serialize(errorReport);

        assertEquals("message=test%20message&stackTrace=stack%20trace", result);
    }

    public void testSerializeWhenPropertyIsNullSetsEmpty() {
        ErrorReport errorReport = new ErrorReport();
        errorReport.setMessage(null);
        errorReport.setStackTrace(null);

        String result = subject.serialize(errorReport);

        assertEquals("message=&stackTrace=", result);
    }

    public void testSerializeAlwaysAddsDataFromAdditionalData() {
        ErrorReport errorReport = new ErrorReport();
        errorReport.setMessage("test message");
        errorReport.setStackTrace("stack trace");
        errorReport.addData("OS", "4.2");

        String result = subject.serialize(errorReport);

        assertEquals("message=test%20message&stackTrace=stack%20trace&OS=4.2", result);
    }
}
