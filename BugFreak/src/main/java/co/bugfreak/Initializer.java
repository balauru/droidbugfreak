package co.bugfreak;

import java.lang.reflect.Type;

import co.bugfreak.components.ErrorHandler;
import co.bugfreak.components.ErrorHandlerImpl;
import co.bugfreak.components.ErrorQueueImpl;
import co.bugfreak.components.ErrorQueue;
import co.bugfreak.components.ErrorReportSerializer;
import co.bugfreak.components.ErrorReportStorage;
import co.bugfreak.components.FormErrorReportSerializer;
import co.bugfreak.components.RemoteErrorReportStorage;
import co.bugfreak.components.ReportRequestBuilder;
import co.bugfreak.components.ReportRequestBuilderImpl;
import co.bugfreak.framework.InstanceCreator;
import co.bugfreak.framework.ServiceLocator;
import co.bugfreak.framework.SimpleServiceLocator;
import co.bugfreak.utils.StringUtils;

public class Initializer {
  public static void initialize() throws IllegalArgumentException {
    setDefaults();
    verifySettings();
    initServices();
    initReporter();
  }

  private static void setDefaults() {
    if (!isValidUrl(GlobalConfig.getServiceEndPoint())) {
      GlobalConfig.setServiceEndPoint("https://www.bugfreak.co/v1/api/errors");
    }
  }

  private static void verifySettings() throws IllegalArgumentException {
    if (StringUtils.isNullOrEmpty(GlobalConfig.getToken())) {
      throw new IllegalArgumentException("Token not set");
    }
    if (!isValidUrl(GlobalConfig.getServiceEndPoint())) {
      throw new IllegalArgumentException("Invalid Service End Point");
    }
    if (StringUtils.isNullOrEmpty(GlobalConfig.getApiKey())) {
      throw new IllegalArgumentException("Api Key not set");
    }
  }

  private static void initServices() {
    SimpleServiceLocator serviceLocator = new SimpleServiceLocator();

    GlobalConfig.setServiceLocator(serviceLocator);
    GlobalConfig.setErrorReportSerializer(new FormErrorReportSerializer());

    ErrorQueue errorQueue = new ErrorQueueImpl();
    serviceLocator.addServiceCreator(ErrorReportSerializer.class, new InstanceCreator() {
      @Override
      public Object create(ServiceLocator locator, Type type) {
        return GlobalConfig.getErrorReportSerializer();
      }
    });
    serviceLocator.addServiceCreator(ReportRequestBuilder.class, new InstanceCreator() {
      @Override
      public Object create(ServiceLocator locator, Type type) {
        return new ReportRequestBuilderImpl();
      }
    });
    serviceLocator.addServiceCreator(ErrorReportStorage.class, new InstanceCreator() {
      @Override
      public Object create(ServiceLocator locator, Type type) {
        return new RemoteErrorReportStorage();
      }
    });
    serviceLocator.addService(ErrorQueue.class, errorQueue);

    ErrorHandler errorHandler = new ErrorHandlerImpl();
    serviceLocator.addService(ErrorHandler.class, errorHandler);
  }

  private static void initReporter() {
    BugFreak.instance = new BugFreak();
  }

  private static boolean isValidUrl(String url) {
    if (url == null) {
      return false;
    }

    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
    return url.matches(urlPattern);
  }
}
