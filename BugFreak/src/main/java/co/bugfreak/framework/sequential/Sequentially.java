package co.bugfreak.framework.sequential;

public class Sequentially {

  public static void execute(Iterable<Result> iterable) {
    new SequentialResult(iterable).execute(new ExecutionContext());
  }
}
