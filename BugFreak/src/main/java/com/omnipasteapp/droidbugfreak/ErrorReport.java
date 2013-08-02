package com.omnipasteapp.droidbugfreak;

import java.util.HashMap;

public class ErrorReport {

  private String message;
  private String stackTrace;
  private HashMap<String, String> additionalData;

  public ErrorReport() {
    additionalData = new HashMap<String, String>();
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setStackTrace(String stackTrace) {
    this.stackTrace = stackTrace;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public void addData(String key, String value) {
    additionalData.put(key, value);
  }

  public HashMap<String, String> getAdditionalData() {
    return additionalData;
  }

  public static ErrorReport fromException(Throwable throwable) {
    if (throwable == null) {
      return null;
    }

    ErrorReport report = new ErrorReport();

    // set message
    report.setMessage(throwable.getMessage());

    // set stack trace
    StringBuilder stringBuilder = new StringBuilder();
    for (StackTraceElement element : throwable.getStackTrace()) {
      stringBuilder.append(String.format("%s %s %d\r\n", element.getClassName(), element.getMethodName(), element.getLineNumber()));
    }
    report.setStackTrace(stringBuilder.toString());

    return report;
  }
}
