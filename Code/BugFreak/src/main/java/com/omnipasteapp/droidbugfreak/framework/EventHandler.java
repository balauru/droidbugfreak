package com.omnipasteapp.droidbugfreak.framework;

public interface EventHandler<T> {
  void handle(Object sender, T eventArgs);
}
