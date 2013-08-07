package co.bugfreak.collections;

import co.bugfreak.framework.EventHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ObservableList<T> implements List<T> {

  public class ListChangedEventArgs {
    private int index;
    private T item;

    public ListChangedEventArgs(int index, T item) {
      setIndex(index);
      setItem(item);
    }

    public int getIndex() {
      return index;
    }

    public void setIndex(int index) {
      this.index = index;
    }

    public T getItem() {
      return item;
    }

    public void setItem(T item) {
      this.item = item;
    }
  }

  private List<EventHandler<ListChangedEventArgs>> listChangedHandlers;
  private List<EventHandler<ListChangedEventArgs>> itemAddedHandlers;
  private List<EventHandler<ListChangedEventArgs>> itemRemovedHandlers;
  private List<T> items;

  public ObservableList() {
    listChangedHandlers = new ArrayList<EventHandler<ListChangedEventArgs>>();
    itemAddedHandlers = new ArrayList<EventHandler<ListChangedEventArgs>>();
    itemRemovedHandlers = new ArrayList<EventHandler<ListChangedEventArgs>>();
    items = new ArrayList<T>();
  }

  public void addListChangedListener(EventHandler<ListChangedEventArgs> handler) {
    listChangedHandlers.add(handler);
  }

  public void removeListChangedListener(EventHandler<ListChangedEventArgs> handler) {
    listChangedHandlers.remove(handler);
  }

  public void addItemAddedListener(EventHandler<ListChangedEventArgs> handler) {
    itemAddedHandlers.add(handler);
  }

  public void removeItemAddedListener(EventHandler<ListChangedEventArgs> handler) {
    itemAddedHandlers.remove(handler);
  }

  public void addItemRemovedListener(EventHandler<ListChangedEventArgs> handler) {
    itemRemovedHandlers.add(handler);
  }

  public void removeItemRemovedListener(EventHandler<ListChangedEventArgs> handler) {
    itemRemovedHandlers.remove(handler);
  }

  public <EA> void raise(Iterable<EventHandler<EA>> handlers, Object sender, EA eventArgs) {
    for (EventHandler<EA> handler : handlers) {
      handler.handle(sender, eventArgs);
    }
  }

  @Override
  public void add(int location, T object) {
    items.add(location, object);

    raise(itemAddedHandlers, this, new ListChangedEventArgs(location, object));
    raise(listChangedHandlers, this, new ListChangedEventArgs(items.indexOf(object), object));
  }

  @Override
  public boolean add(T object) {
    boolean result = items.add(object);

    raise(itemAddedHandlers, this, new ListChangedEventArgs(items.indexOf(object), object));
    raise(listChangedHandlers, this, new ListChangedEventArgs(items.indexOf(object), object));

    return result;
  }

  @Override
  public boolean addAll(int location, Collection<? extends T> collection) {
    boolean result = items.addAll(location, collection);

    for (T item : collection) {
      if (items.contains(item)) {
        raise(itemAddedHandlers, this, new ListChangedEventArgs(items.indexOf(item), item));
        raise(listChangedHandlers, this, new ListChangedEventArgs(items.indexOf(item), item));
      }
    }

    return result;
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    boolean result = items.addAll(collection);

    for (T item : collection) {
      if (items.contains(item)) {
        raise(itemAddedHandlers, this, new ListChangedEventArgs(items.indexOf(item), item));
        raise(listChangedHandlers, this, new ListChangedEventArgs(items.indexOf(item), item));
      }
    }

    return result;
  }

  @Override
  public void clear() {
    items.clear();
  }

  @Override
  public boolean contains(Object object) {
    return items.contains(object);
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    return items.containsAll(collection);
  }

  @Override
  public T get(int location) {
    return items.get(location);
  }

  @Override
  public int indexOf(Object object) {
    return items.indexOf(object);
  }

  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

  @Override
  public Iterator<T> iterator() {
    return items.iterator();
  }

  @Override
  public int lastIndexOf(Object object) {
    return items.lastIndexOf(object);
  }

  @Override
  public ListIterator<T> listIterator() {
    return items.listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int location) {
    return items.listIterator(location);
  }

  @Override
  public T remove(int location) {
    T item = items.remove(location);

    raise(itemRemovedHandlers, this, new ListChangedEventArgs(location, item));
    raise(listChangedHandlers, this, new ListChangedEventArgs(location, item));

    return item;
  }

  @Override
  public boolean remove(Object object) {
    int location = indexOf(object);
    boolean result = items.remove(object);

    raise(itemRemovedHandlers, this, new ListChangedEventArgs(location, (T) object));
    raise(listChangedHandlers, this, new ListChangedEventArgs(location, (T) object));

    return result;
  }

  @Override
  public boolean removeAll(Collection<?> collection) {

    for (Object item : collection) {
      if (items.contains(item)) {
        raise(itemAddedHandlers, this, new ListChangedEventArgs(items.indexOf(item), (T) item));
        raise(listChangedHandlers, this, new ListChangedEventArgs(items.indexOf(item), (T) item));
      }
    }

    return items.removeAll(collection);
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    return items.retainAll(collection);
  }

  @Override
  public T set(int location, T object) {
    return items.set(location, object);
  }

  @Override
  public int size() {
    return items.size();
  }

  @Override
  public List<T> subList(int start, int end) {
    return items.subList(start, end);
  }

  @Override
  public Object[] toArray() {
    return items.toArray();
  }

  @Override
  public <T1> T1[] toArray(T1[] array) {
    return items.toArray(array);
  }

}
