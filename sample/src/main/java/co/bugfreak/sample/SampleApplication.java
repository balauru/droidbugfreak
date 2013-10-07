package co.bugfreak.sample;

import android.app.Application;

import co.bugfreak.BugFreak;

public class SampleApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    // demo@bugfreak.co demouser
    BugFreak.hook("9dd7f8aa-6b29-4022-80fa-441609ca1547", "520cd6f887e36daaaf000004", this);
  }
}
