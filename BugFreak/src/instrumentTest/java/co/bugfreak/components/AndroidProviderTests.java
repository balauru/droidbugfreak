package co.bugfreak.components;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.test.InstrumentationTestCase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class AndroidProviderTests extends InstrumentationTestCase {
  @Mock
  private Application mockApplication;

  @Mock
  private ActivityManager mockActivityManager;

  @Mock
  private Context mockContext;

  @Mock
  private PackageManager mockPackageManager;

  private PackageInfo packageInfo;

  private AndroidProvider subject;

  protected void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(mockApplication.getApplicationContext()).thenReturn(mockContext);
    when(mockContext.getSystemService(Context.ACTIVITY_SERVICE)).thenReturn(mockActivityManager);
    when(mockApplication.getPackageManager()).thenReturn(mockPackageManager);
    packageInfo = new PackageInfo();
    when(mockPackageManager.getPackageInfo(anyString(), eq(0))).thenReturn(packageInfo);


    subject = new AndroidProvider(mockApplication);
  }

  public void testGetDataContainsApiLevel() {
    HashMap<String, String> data = subject.getData();

    assertEquals(Integer.toString(Build.VERSION.SDK_INT), data.get("ApiLevel"));
  }

  public void testGetDataContainsCodename() {
    HashMap<String, String> data = subject.getData();

    assertEquals(Build.VERSION.CODENAME, data.get("Codename"));
  }

  public void testGetDataContainsManufacturer() {
    HashMap<String, String> data = subject.getData();

    assertEquals(Build.MANUFACTURER, data.get("Manufacturer"));
  }

  public void testGetDataContainsModel() {
    HashMap<String, String> data = subject.getData();

    assertEquals(Build.MODEL, data.get("Model"));
  }

  public void testGetDataContainsAppVersionNumber() {
    packageInfo.versionCode = 42;

    HashMap<String, String> data = subject.getData();

    assertEquals("42", data.get("AppVersionNumber"));
  }

  public void testGetDataContainsAppVersionName() {
    packageInfo.versionName = "op 4.2";

    HashMap<String, String> data = subject.getData();

    assertEquals("op 4.2", data.get("AppVersionName"));
  }
}
