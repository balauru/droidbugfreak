package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.utils.StringUtils;

import java.util.Map;

public class FormErrorReportSerializer implements ErrorReportSerializer {
  private final String ContentType = "application/x-www-form-urlencoded";
  private final String Format = "%s=%s";
  private final String Separator = "&";

  public String getContentType() {
    return ContentType;
  }

  public String serialize(ErrorReport report) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(String.format(Format, "message", Encode(report.getMessage())));
    stringBuilder.append(Separator);
    stringBuilder.append(String.format(Format, "stackTrace", Encode(report.getStackTrace())));

    for (Map.Entry<String, String> entry : report.getAdditionalData().entrySet()) {
      stringBuilder.append(Separator);
      stringBuilder.append(String.format(Format, entry.getKey(), entry.getValue()));
    }

    return stringBuilder.toString();
  }

  private String Encode(String source) {
    String result = StringUtils.empty();

    if (!StringUtils.isNullOrEmpty(source)) {
      result = StringUtils.urlEncode(source);
    }

    return result;
  }
}
