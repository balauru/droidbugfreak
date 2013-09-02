package co.bugfreak;

import java.util.ArrayList;
import java.util.List;

import co.bugfreak.components.ErrorReportDataProvider;
import co.bugfreak.components.ErrorReportSerializer;
import co.bugfreak.framework.ServiceLocator;

public class GlobalConfig {

  private static String serviceEndPoint;
  private static String apiKey;
  private static String token;
  private static List<ErrorReportDataProvider> providers = new ArrayList<ErrorReportDataProvider>();
  private static ServiceLocator serviceLocator;
  private static ErrorReportSerializer errorReportSerializer;

  public static ServiceLocator getServiceLocator() {
    return serviceLocator;
  }

  public static void setServiceLocator(ServiceLocator serviceLocator) {
    GlobalConfig.serviceLocator = serviceLocator;
  }

  public static ErrorReportSerializer getErrorReportSerializer() {
    return errorReportSerializer;
  }

  public static void setErrorReportSerializer(ErrorReportSerializer errorReportSerializer) {
    GlobalConfig.errorReportSerializer = errorReportSerializer;
  }

  public static void addDataProvider(ErrorReportDataProvider provider) {
    providers.add(provider);
  }

  public static List<ErrorReportDataProvider> getDataProviders() {
    return providers;
  }

  public static String getServiceEndPoint() {
    return GlobalConfig.serviceEndPoint;
  }

  public static void setServiceEndPoint(String serviceEndPoint) {
    GlobalConfig.serviceEndPoint = serviceEndPoint;
  }

  public static String getApiKey() {
    return GlobalConfig.apiKey;
  }

  public static void setApiKey(String apiKey) {
    GlobalConfig.apiKey = apiKey;
  }

  public static String getToken() {
    return GlobalConfig.token;
  }

  public static void setToken(String token) {
    GlobalConfig.token = token;
  }
}

