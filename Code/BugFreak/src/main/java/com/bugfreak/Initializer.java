package com.bugfreak;

import com.bugfreak.components.ErrorReportHandler;
import com.bugfreak.components.ErrorReportHandlerImpl;
import com.bugfreak.components.ErrorReportQueue;
import com.bugfreak.components.ErrorReportQueueImpl;
import com.bugfreak.components.ErrorReportQueueListener;
import com.bugfreak.components.ErrorReportQueueListenerImpl;
import com.bugfreak.components.ErrorReportSerializer;
import com.bugfreak.components.ErrorReportStorage;
import com.bugfreak.components.FormErrorReportSerializer;
import com.bugfreak.components.RemoteErrorReportStorage;
import com.bugfreak.components.ReportRequestBuilder;
import com.bugfreak.components.ReportRequestBuilderImpl;
import com.bugfreak.framework.InstanceCreator;
import com.bugfreak.framework.ServiceLocator;
import com.bugfreak.framework.SimpleServiceLocator;
import com.bugfreak.utils.StringUtils;

import java.lang.reflect.Type;

public class Initializer {
  public static void initialize() throws IllegalArgumentException {
    verifySettings();
    initServices();
    initReporter();
  }

  private static void verifySettings() throws IllegalArgumentException {
    if (!StringUtils.isNullOrEmpty(GlobalConfig.Settings.getAppName())) {
      throw new IllegalArgumentException("AppName not set");
    }
    if (!StringUtils.isNullOrEmpty(GlobalConfig.Settings.getToken())) {
      throw new IllegalArgumentException("Token not set");
    }
    if (!isValidUrl(GlobalConfig.Settings.getServiceEndPoint())) {
      throw new IllegalArgumentException("Invalid Service End Point");
    }
    if (!StringUtils.isNullOrEmpty(GlobalConfig.Settings.getApiKey())) {
      throw new IllegalArgumentException("Api Key not set");
    }
  }

  private static void initServices() {
    SimpleServiceLocator serviceLocator = new SimpleServiceLocator();

    GlobalConfig.setServiceLocator(serviceLocator);
    GlobalConfig.setErrorReportSerializer(new FormErrorReportSerializer());

    ErrorReportQueue errorReportQueue = new ErrorReportQueueImpl();
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
    serviceLocator.addService(ErrorReportQueue.class, errorReportQueue);

    ErrorReportHandler errorHandler = new ErrorReportHandlerImpl();
    serviceLocator.addService(ErrorReportHandler.class, errorHandler);

    ErrorReportQueueListener errorQueueListener = new ErrorReportQueueListenerImpl();
    serviceLocator.addService(ErrorReportQueueListener.class, errorQueueListener);

    errorQueueListener.listen(errorReportQueue);
  }

  private static void initReporter() {
    AgileReporter.instance = new AgileReporter();
  }

  private static boolean isValidUrl(String url) {
    if (url == null) {
      return false;
    }

    String urlPattern = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";
    return url.matches(urlPattern);
  }
}
