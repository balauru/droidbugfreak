package co.bugfreak.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringUtils {

  public static boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static String empty() {
    return "";
  }

  public static String urlEncode(String source) {
    try {
      return URLEncoder.encode(source, "UTF-8").replace("+", "%20");
    } catch (UnsupportedEncodingException e) {
      return empty();
    }
  }
}
