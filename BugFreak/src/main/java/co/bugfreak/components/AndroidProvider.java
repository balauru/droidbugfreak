package co.bugfreak.components;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;

import java.util.HashMap;

public class AndroidProvider implements ErrorReportDataProvider {
  private final Application app;
  private final ActivityManager activityManager;

  public AndroidProvider(Application app) {
    this.app = app;
    activityManager = (ActivityManager) app.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
  }

  @Override
  public HashMap<String, String> getData() {
    HashMap<String, String> map = new HashMap<String, String>();

    putHardwareInfo(map);
    putSoftwareInfo(map);
    putMemoryInfo(map);
    putAppInfo(map);

    return map;
  }

  private void putHardwareInfo(HashMap<String, String> map) {
    map.put("Manufacturer", Build.MANUFACTURER);
    map.put("Brand", Build.BRAND);
    map.put("Model", Build.MODEL);
  }

  private void putSoftwareInfo(HashMap<String, String> map) {
    map.put("ApiLevel", Integer.toString(Build.VERSION.SDK_INT));
    map.put("Codename", Build.VERSION.CODENAME);
  }

  private void putMemoryInfo(HashMap<String, String> map) {
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);

    map.put("Treshold", Double.toString(toMegaBytes(memoryInfo.threshold)));
    map.put("AvailableMemory", Double.toString(toMegaBytes(memoryInfo.availMem)));
    map.put("Pss", Double.toString(toMegaBytes(Debug.getPss())));
    map.put("LowMemory", Boolean.toString(memoryInfo.lowMemory));
  }

  private void putAppInfo(HashMap<String, String> map) {
    try {
      PackageInfo packageInfo = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);

      map.put("AppVersionNumber", Integer.toString(packageInfo.versionCode));
      map.put("AppVersionName", packageInfo.versionName);

    } catch (PackageManager.NameNotFoundException e) {
      // package does exists
    }
  }

  private double toMegaBytes(Long number) {
    final long megaByteDivider = 1048576L;

    return (double) number / megaByteDivider;
  }
}
