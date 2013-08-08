package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.utils.StringUtils;

import java.util.Map;

public class FormErrorReportSerializer implements ErrorReportSerializer {
  private static final String ContentType = "application/x-www-form-urlencoded";
  private static final String Format = "%s=%s";
  private static final String AdditionalDataFormat = "additionalData[%s]=%s";
  private static final String Separator = "&";

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
      stringBuilder.append(String.format(AdditionalDataFormat, entry.getKey(), Encode(entry.getValue())));
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