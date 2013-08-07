package com.omnipasteapp.droidbugfreak;

import com.omnipasteapp.droidbugfreak.components.ErrorReportDataProvider;
import com.omnipasteapp.droidbugfreak.components.ErrorReportSerializer;
import com.omnipasteapp.droidbugfreak.framework.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfig {

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

  public static class Settings {
    private static String serviceEndPoint;
    private static String apiKey;
    private static String token;

    public static String getServiceEndPoint() {
      return Settings.serviceEndPoint;
    }

    public static void setServiceEndPoint(String serviceEndPoint) {
      Settings.serviceEndPoint = serviceEndPoint;
    }

    public static String getApiKey() {
      return Settings.apiKey;
    }

    public static void setApiKey(String apiKey) {
      Settings.apiKey = apiKey;
    }

    public static String getToken() {
      return Settings.token;
    }

    public static void setToken(String token) {
      Settings.token = token;
    }
  }
}

