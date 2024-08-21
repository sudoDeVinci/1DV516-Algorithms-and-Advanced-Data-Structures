package src.Task5;

import java.util.Iterator;

/**
 * generic deque (double-ended queue). In a deque you can add and remove from either
 * end so that it can work either as a stack or a queue. 
 */
public class Deque<T> implements Iterable<T>{

  private LinkedList<T> list;

  public Deque() {
    list = new LinkedList<T>();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public int size() {
    return list.size();
  }

  public void addFirst(T data) {
    list.prepend(data);
  }

  public void addLast(T data) {
    list.append(data);
  }

  public T removeFirst() {
    return list.removeFirst();
  }

  public T removeLast() {
    return list.removeLast();
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}