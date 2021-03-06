package co.bugfreak.components;

import co.bugfreak.ErrorReport;

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
        errorReport.addData("API_LEVEL", "18");

        String result = subject.serialize(errorReport);

        assertEquals("message=test%20message&stackTrace=stack%20trace&additionalData[OS]=4.2&additionalData[API_LEVEL]=18", result);
    }
}
