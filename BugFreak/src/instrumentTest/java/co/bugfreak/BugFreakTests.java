package co.bugfreak;

import junit.framework.Assert;
import junit.framework.TestCase;

public class BugFreakTests extends TestCase {

  public void tearDown(){
    GlobalConfig.setToken(null);
    GlobalConfig.setApiKey(null);
    GlobalConfig.setServiceEndPoint(null);
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
    GlobalConfig.setToken("token");

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
    GlobalConfig.setToken("token");
    GlobalConfig.setApiKey("apiKey");
    GlobalConfig.setServiceEndPoint("http://google.ro");

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
    GlobalConfig.setToken("token");
    GlobalConfig.setApiKey("apiKey");

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
    GlobalConfig.setToken("token");
    GlobalConfig.setApiKey("apiKey");
    GlobalConfig.setServiceEndPoint("http://google.ro");

    BugFreak.init();

    assertEquals("http://google.ro", GlobalConfig.getServiceEndPoint());
  }

  public void testInitializeWhenServiceEndpointIsSetDoesNotOverwrite() {
    GlobalConfig.setToken("token");
    GlobalConfig.setApiKey("apiKey");

    BugFreak.init();

    assertEquals("https://www.bugfreak.co/v1/api/errors", GlobalConfig.getServiceEndPoint());
  }
}
