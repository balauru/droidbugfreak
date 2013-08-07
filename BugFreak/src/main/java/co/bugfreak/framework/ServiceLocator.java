package co.bugfreak.framework;

public interface ServiceLocator {
  <T> T getService(Class<T> clazz);

  <T> Iterable<T> getServices(Class<T> clazz);
}
