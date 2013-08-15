package co.bugfreak.sample;

import android.app.Application;

import co.bugfreak.BugFreak;
import co.bugfreak.GlobalConfig;

/**
 * Created by calin on 8/15/13.
 */
public class SampleApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    GlobalConfig.Settings.setApiKey("9dd7f8aa-6b29-4022-80fa-441609ca1547");
    GlobalConfig.Settings.setToken("520cd6f887e36daaaf000004");

    BugFreak.hook(this);
  }
}
