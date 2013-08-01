package com.omnipasteapp.droidbugfreak;

import com.omnipasteapp.droidbugfreak.components.ErrorReportHandler;
import com.omnipasteapp.droidbugfreak.components.ErrorReportHandlerImpl;
import com.omnipasteapp.droidbugfreak.components.ErrorReportQueue;
import com.omnipasteapp.droidbugfreak.components.ErrorReportQueueImpl;
import com.omnipasteapp.droidbugfreak.components.ErrorReportQueueListener;
import com.omnipasteapp.droidbugfreak.components.ErrorReportQueueListenerImpl;
import com.omnipasteapp.droidbugfreak.components.ErrorReportSerializer;
import com.omnipasteapp.droidbugfreak.components.ErrorReportStorage;
import com.omnipasteapp.droidbugfreak.components.FormErrorReportSerializer;
import com.omnipasteapp.droidbugfreak.components.RemoteErrorReportStorage;
import com.omnipasteapp.droidbugfreak.components.ReportRequestBuilder;
import com.omnipasteapp.droidbugfreak.components.ReportRequestBuilderImpl;
import com.omnipasteapp.droidbugfreak.framework.InstanceCreator;
import com.omnipasteapp.droidbugfreak.framework.ServiceLocator;
import com.omnipasteapp.droidbugfreak.framework.SimpleServiceLocator;
import com.omnipasteapp.droidbugfreak.utils.StringUtils;

import java.lang.reflect.Type;

public class Initializer {
  public static void initialize() throws IllegalArgumentException {
    verifySettings();
    initServices();
    initReporter();
  }

  private static void verifySettings() throws IllegalArgumentException {
    if (StringUtils.isNullOrEmpty(GlobalConfig.Settings.getToken())) {
      throw new IllegalArgumentException("Token not set");
    }
    if (!isValidUrl(GlobalConfig.Settings.getServiceEndPoint())) {
      throw new IllegalArgumentException("Invalid Service End Point");
    }
    if (StringUtils.isNullOrEmpty(GlobalConfig.Settings.getApiKey())) {
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
