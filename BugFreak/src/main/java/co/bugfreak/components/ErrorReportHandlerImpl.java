package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.Sequentially;
import co.bugfreak.framework.yieldreturn.Generator;
import co.bugfreak.results.SaveReportResult;
import co.bugfreak.utils.Array;

import java.util.List;

public class ErrorReportHandlerImpl implements ErrorReportHandler {

  private List<ErrorReportStorage> storageLocations;

  public ErrorReportHandlerImpl() {
    storageLocations = Array.toList(GlobalConfig.getServiceLocator().getServices(ErrorReportStorage.class));
  }

  @Override
  public void handle(ErrorReport report) {
    Sequentially.execute(new StoreReportProcedure(report));
  }

  @Override
  public void dispose() {
    storageLocations = null;
  }

  class StoreReportProcedure extends Generator<Result> {

    private final ErrorReport report;

    StoreReportProcedure(ErrorReport report) {
      this.report = report;
    }

    @Override
    protected void run() {
      for (ErrorReportStorage storage : storageLocations) {
        yield(new SaveReportResult(storage, report));
      }
    }
  }
}
