package co.bugfreak.components;

import java.util.List;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.ReportCompletedCallback;
import co.bugfreak.collections.ObservableList;
import co.bugfreak.framework.EventHandler;
import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.ResultCompletedArgs;
import co.bugfreak.framework.sequential.ResultCompletedListener;
import co.bugfreak.framework.sequential.Sequentially;
import co.bugfreak.framework.yieldreturn.Generator;
import co.bugfreak.results.SaveReportResult;
import co.bugfreak.utils.Array;

public class ErrorHandlerImpl implements ErrorHandler, EventHandler<ObservableList<PendingReport>.ListChangedEventArgs> {
  private ErrorQueue errorQueue;

  public ErrorHandlerImpl() {
    errorQueue = GlobalConfig.getServiceLocator().getService(ErrorQueue.class);
    errorQueue.addItemAddedListener(this);
  }

  @Override
  public void handle(Throwable throwable, ReportCompletedCallback reportCompletedCallback) {
    errorQueue.enqueue(new PendingReport(throwable, reportCompletedCallback));
  }

  @Override
  public void dispose() {
    errorQueue = null;
  }

  @Override
  public void handle(Object sender, final ObservableList<PendingReport>.ListChangedEventArgs e) {
    PendingReport item = errorQueue.dequeue();
    ErrorReport report = ErrorReport.fromException(item.getThrowable());

    Sequentially.execute(new StoreReportProcedure(report), new ResultCompletedListener() {
      @Override
      public void handle(Result result, ResultCompletedArgs args) {
        if (e.getItem().getReportCompletedCallback() != null) {
          e.getItem().getReportCompletedCallback().onCompleted(e.getItem().getThrowable(), args.wasCancelled());
        }
      }
    });
  }

  class StoreReportProcedure extends Generator<Result> {

    private final ErrorReport report;

    StoreReportProcedure(ErrorReport report) {
      this.report = report;
    }

    @Override
    protected void run() {
      List<ErrorReportStorage> storageLocations = Array.toList(GlobalConfig.getServiceLocator().getServices(ErrorReportStorage.class));
      for (ErrorReportStorage storage : storageLocations) {
        yield(new SaveReportResult(storage, report));
      }
    }
  }
}
