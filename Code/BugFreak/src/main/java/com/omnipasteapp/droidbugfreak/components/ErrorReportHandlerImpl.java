package com.omnipasteapp.droidbugfreak.components;

import com.omnipasteapp.droidbugfreak.ErrorReport;
import com.omnipasteapp.droidbugfreak.GlobalConfig;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportHandlerImpl implements ErrorReportHandler {

  private List<ErrorReportStorage> storageLocations;

  public ErrorReportHandlerImpl() {
    storageLocations = createList(GlobalConfig.getServiceLocator().getServices(ErrorReportStorage.class));
  }

  @Override
  public void handle(ErrorReport report) {
    for (ErrorReportStorage storage : storageLocations) {
      try {
        if (storage.save(report)) {
          break;
        }
      } catch (Throwable throwable) {

      }
    }
  }

  @Override
  public void dispose() {
    storageLocations = null;
  }

  private <T> List<T> createList(Iterable<T> iterable) {
    List<T> list = new ArrayList<T>();
    for (T item : iterable) {
      list.add(item);
    }

    return list;
  }
}
