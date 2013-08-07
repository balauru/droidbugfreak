package co.bugfreak.components;

import junit.framework.Assert;
import junit.framework.TestCase;

import co.bugfreak.BugFreak;
import co.bugfreak.GlobalConfig;

public class BugFreakTests extends TestCase {

  public void tearDown(){
    GlobalConfig.Settings.setToken(null);
    GlobalConfig.Settings.setApiKey(null);
    GlobalConfig.Settings.setServiceEndPoint(null);
  }

  public void testInitializeWhenTokenIsNotSetRaisesIllegalArgumentException() {
    IllegalArgumentException exception = null;

    try {
      BugFreak.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception == null) {
      Assert.fail();
    }
  }

  public void testInitializeWhenTokenSetAndApiKeyNotSetRaisesIllegalArgumentException() {
    IllegalArgumentException exception = null;
    GlobalConfig.Settings.setToken("token");

    try {
      BugFreak.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception == null) {
      Assert.fail();
    }
  }

  public void testInitializeWhenAllSettingsAreSetDoesNotRaiseException() {
    IllegalArgumentException exception = null;
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setApiKey("apiKey");
    GlobalConfig.Settings.setServiceEndPoint("http://google.ro");

    try {
      BugFreak.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception != null) {
      Assert.fail();
    }
  }

  public void testInitializeWhenServiceEndpointNotSetDoesNotRaiseIllegalArgumentException() {
    IllegalArgumentException exception = null;
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setApiKey("apiKey");

    try {
      BugFreak.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception != null) {
      Assert.fail();
    }
  }

  public void testInitializeWhenServiceEndpointNotSetSetsDefault() {
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setApiKey("apiKey");
    GlobalConfig.Settings.setServiceEndPoint("http://google.ro");

    BugFreak.init();

    assertEquals("http://google.ro", GlobalConfig.Settings.getServiceEndPoint());
  }

  public void testInitializeWhenServiceEndpointIsSetDoesNotOverwrite() {
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setApiKey("apiKey");

    BugFreak.init();

    assertEquals("http://bugfreak.co/v1/api/errors", GlobalConfig.Settings.getServiceEndPoint());
  }
}
