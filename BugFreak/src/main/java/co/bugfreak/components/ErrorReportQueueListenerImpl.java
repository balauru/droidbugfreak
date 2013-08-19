package co.bugfreak.components;

import co.bugfreak.ErrorReport;
import co.bugfreak.GlobalConfig;
import co.bugfreak.collections.ObservableList;
import co.bugfreak.framework.EventHandler;
import co.bugfreak.framework.ExecutionContext;
import co.bugfreak.framework.SequentialResult;

public class ErrorReportQueueListenerImpl implements ErrorReportQueueListener, EventHandler<ObservableList<ErrorReport>.ListChangedEventArgs> {
  private ErrorReportQueue errorReportQueue;
  private ErrorReportHandler errorReportHandler;

  @Override
  public void listen(ErrorReportQueue queue) {
    errorReportHandler = GlobalConfig.getServiceLocator().getService(ErrorReportHandler.class);

    errorReportQueue = queue;
    errorReportQueue.addItemAddedListener(this);
  }

  @Override
  public void dispose() {
    errorReportQueue.removeItemAddedListener(this);
    errorReportQueue = null;
    errorReportHandler.dispose();
    errorReportHandler = null;
  }

  @Override
  public void handle(Object sender, ObservableList<ErrorReport>.ListChangedEventArgs eventArgs) {
    new SequentialResult(errorReportHandler.handle(eventArgs.getItem())).execute(new ExecutionContext());
  }
}
