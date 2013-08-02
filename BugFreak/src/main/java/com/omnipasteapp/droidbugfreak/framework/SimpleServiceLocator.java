package com.omnipasteapp.droidbugfreak.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleServiceLocator implements ServiceLocator {

  private final HashMap<Class<?>, List<Object>> _instanceDictionary;
  private final HashMap<Class<?>, List<InstanceCreator>> _instanceCreatorDictionary;

  public SimpleServiceLocator() {
    _instanceDictionary = new HashMap<Class<?>, List<Object>>();
    _instanceCreatorDictionary = new HashMap<Class<?>, List<InstanceCreator>>();
  }

  @Override
  public <T> T getService(Class<T> clazz) {
    Object instance = null;

    if (_instanceDictionary.containsKey(clazz)) {
      instance = _instanceDictionary.get(clazz).get(0);
    } else if (_instanceCreatorDictionary.containsKey(clazz)) {
      instance = _instanceCreatorDictionary.get(clazz).get(0).create(this, clazz);
    }

    return (T) instance;
  }

  @Override
  public <T> Iterable<T> getServices(Class<T> clazz) {
    List<T> result = new ArrayList<T>();

    Iterable<Object> instances = getValues(_instanceDictionary, clazz);
    for (Object instance : instances) {
      result.add((T) instance);
    }

    Iterable<InstanceCreator> creators = getValues(_instanceCreatorDictionary, clazz);
    for (InstanceCreator creator : creators) {
      result.add((T) creator.create(this, clazz));
    }

    return result;
  }

  public void addServiceCreator(Class<?> type, InstanceCreator creator) {
    if (_instanceCreatorDictionary.containsKey(type)) {
      _instanceCreatorDictionary.get(type).add(creator);
    } else {
      List<InstanceCreator> value = new ArrayList<InstanceCreator>();
      value.add(creator);
      _instanceCreatorDictionary.put(type, value);
    }
  }

  public void addService(Class<?> type, Object instance) {
    if (_instanceDictionary.containsKey(type)) {
      _instanceDictionary.get(type).add(instance);
    } else {
      List<Object> value = new ArrayList<Object>();
      value.add(instance);
      _instanceDictionary.put(type, value);
    }
  }

  private <T> Iterable<T> getValues(HashMap<Class<?>, List<T>> dictionary, Class<?> key) {
    List<T> result = new ArrayList<T>();

    for (Class<?> entry : dictionary.keySet()) {
      if (key.isAssignableFrom(entry)) {
        result.addAll(dictionary.get(key));
      }
    }

    return result;
  }
}
