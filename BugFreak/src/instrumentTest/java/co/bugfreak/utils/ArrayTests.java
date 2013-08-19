package co.bugfreak.utils;

import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class ArrayTests extends TestCase {

  public void testToListAlwaysReturnsListOfElements() {
    LinkedList<Object> list = new LinkedList<Object>();
    Object obj1 = new Object();
    list.add(obj1);
    Object obj2 = new Object();
    list.add(obj2);

    List<Object> result = Array.toList(list);

    assertNotSame(list, result);
    assertTrue(result.contains(obj1));
    assertTrue(result.contains(obj2));
  }
}
