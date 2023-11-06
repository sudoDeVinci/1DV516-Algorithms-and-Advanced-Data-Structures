package src;

public class Deque<T> {
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
}