package com.omnipasteapp.droidbugfreak;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AgileReporterTests extends TestCase {

  public void tearDown(){
    GlobalConfig.Settings.setToken(null);
    GlobalConfig.Settings.setApiKey(null);
    GlobalConfig.Settings.setServiceEndPoint(null);
  }

  public void testInitializeWhenTokenIsNotSetRaisesIllegalArgumentException() {
    IllegalArgumentException exception = null;

    try {
      AgileReporter.init();
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
      AgileReporter.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception == null) {
      Assert.fail();
    }
  }

  public void testInitializeWhenServiceEndpointNotSetRaisesIllegalArgumentException() {
    IllegalArgumentException exception = null;
    GlobalConfig.Settings.setToken("token");
    GlobalConfig.Settings.setApiKey("apiKey");

    try {
      AgileReporter.init();
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
      AgileReporter.init();
    } catch (IllegalArgumentException exc) {
      exception = exc;
    }

    if (exception != null) {
      Assert.fail();
    }
  }
}
