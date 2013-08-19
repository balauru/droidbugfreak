package co.bugfreak.framework.sequential;

public interface Result {
  void addCompleteListener(ResultCompletedListener handler);

  void removeCompleteListener(ResultCompletedListener handler);

  void execute(ExecutionContext context) throws Throwable;
}
