package co.bugfreak.framework.sequential;

import co.bugfreak.framework.sequential.Result;
import co.bugfreak.framework.sequential.ResultCompletedArgs;

public interface ResultCompletedListener {
  void handle(Result result, ResultCompletedArgs args);
}