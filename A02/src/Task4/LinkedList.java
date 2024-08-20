package src.Task4;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {
  private Node<T> first;
  private Node<T> last;

  public LinkedList() {
    first = null;
    last = null;
  }

  @Override
  public Iterator<T> iterator() {
    return new LinkedListIterator(first);
  }

  private class LinkedListIterator implements Iterator<T> {
    private Node<T> current;

    public LinkedListIterator(Node<T> firstNode) {
        this.current = firstNode;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (current == null) {
            throw new NoSuchElementException();
        }
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}

  public void append(T data) {
    if (first == null) {
      first = new Node<T>(data);
      last = first;
    } else {
      last.setNext(new Node<T>(data));
      last = last.getNext();
    }
  }

  public void prepend(T data) {
    if (first == null) {
      first = new Node<T>(data);
      last = first;
    } else {
      Node<T> temp = new Node<T>(data);
      temp.setNext(first);
      first = temp;
    }
  }

  public boolean isEmpty() {
    return first == null;
  }

  public int size() {
    int count = 0;
    Node<T> current = first;
    while (current != null) {
      count++;
      current = current.getNext();
    }
    return count;
  }

  public void delete(T data) {
    Node<T> current = first;
    Node<T> previous = first;
    while (!current.getData().equals(data)) {
      if (current.getNext() == null) { // If we reach the end of the list
        return;
      } else {
        previous = current;
        current = current.getNext();
      }
    }

    if (current == first) {
      first = first.getNext();
    } else {
      previous.setNext(current.getNext());
    }
  } 

  public T removeFirst() {
    if (first == null) {
      throw new RuntimeException("List is empty!");
    }
    T data = first.getData();
    first = first.getNext();
    return data;
  }

  public T removeLast() {
    if (first == null) {
      throw new RuntimeException("List is empty!");
    }
    Node<T> current = first;
    Node<T> previous = first;
    while (current.getNext() != null) {
      previous = current;
      current = current.getNext();
    }
    T data = current.getData();
    previous.setNext(null);
    return data;
  }

}
