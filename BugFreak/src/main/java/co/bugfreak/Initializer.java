package co.bugfreak;

import java.lang.reflect.Type;

import co.bugfreak.components.ErrorReportHandler;
import co.bugfreak.components.ErrorReportHandlerImpl;
import co.bugfreak.components.ErrorReportQueue;
import co.bugfreak.components.ErrorReportQueueImpl;
import co.bugfreak.components.ErrorReportQueueListener;
import co.bugfreak.components.ErrorReportQueueListenerImpl;
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
    if (!isValidUrl(GlobalConfig.Settings.getServiceEndPoint())) {
      GlobalConfig.Settings.setServiceEndPoint("http://bugfreak.co/v1/api/errors");
    }
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
