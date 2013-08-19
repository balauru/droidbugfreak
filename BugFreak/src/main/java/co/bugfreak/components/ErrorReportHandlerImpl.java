package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.results.SaveReportResult;
import co.bugfreak.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportHandlerImpl implements ErrorReportHandler {

  private List<ErrorReportStorage> storageLocations;

  public ErrorReportHandlerImpl() {
    storageLocations = Array.toList(GlobalConfig.getServiceLocator().getServices(ErrorReportStorage.class));
  }

  @Override
  public Iterable<Result> handle(ErrorReport report) {
    ArrayList<Result> results = new ArrayList<Result>();

    for (ErrorReportStorage storage : storageLocations) {
      results.add(new SaveReportResult(storage, report));
    }

    return results;
  }

  @Override
  public void dispose() {
    storageLocations = null;
  }
}
