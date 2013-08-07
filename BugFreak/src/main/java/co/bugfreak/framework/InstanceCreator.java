package co.bugfreak.framework;

import java.lang.reflect.Type;

public interface InstanceCreator {
  Object create(ServiceLocator locator, Type type);
}
