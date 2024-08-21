package src.Task6;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * a randomized queue where each dequeue operation returns a random element from the queue.
 */
public class DequeRand<T> implements Iterable<T> {
  private T[] queue;
  private int inQueue;
  private int startSize;

  /**
   * Initializes an empty deque with a given capacity.
   */
  @SuppressWarnings("unchecked") // Generic array creation is not allowed in Java
  public DequeRand(int size) { // but this *should* be safe
    this.queue = (T[]) new Object[size];
    this.setStartSize(size);
  }

  /**
   * Checks if the deque is empty.
   */
  public boolean isEmpty() {
    return (this.inQueue == 0);
  }

  /**
   * Resizes the array to the given size.
   */
  public int size() {
    return this.inQueue;
  }

  /**
   * Set the initial size of the array inside the queue.
   */
  private void setStartSize(int sz) {
    if (sz <= 0) throw new IllegalArgumentException("Size must be positive");
    this.startSize = sz;
    this.inQueue = 0;
  }

  /**
   * Adds an element to the back of the deque.
   */
  public void enqueue(T data) {
    fitArray();
    queue[this.inQueue++] = data;
  }

  /**
   * resizes the queue to fit the current number of elements.
   */
  public void fitArray() {
    if ((this.queue.length >= (this.startSize * 2)) && ((this.queue.length / 4) == this.inQueue)) {
      resize(this.queue.length / 2);
    } else if (this.inQueue == this.queue.length) {
      resize(2 * queue.length);
    }
  }

  /**
   * Removes and returns the element at the front of the deque.
   */
  public T dequeue() {
    if (this.isEmpty()) throw new NoSuchElementException("Queue is empty!");

    int randomIndex = new Random().nextInt(inQueue);
    T tempdata = queue[randomIndex];
    this.queue[randomIndex] = queue[this.inQueue - 1];
    this.queue[this.inQueue - 1] = tempdata;

    T data = queue[this.inQueue - 1];
    this.inQueue--;

    fitArray();
    return data;
  }

  /**
   * copies the array elements to a resized array.
   */
  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    T[] copy = (T[]) new Object[capacity];
    for (int i = 0; i < inQueue; i++) {
      copy[i] = this.queue[i];
    }
    queue = copy;
  }

  @Override
  public Iterator<T> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<T> {
    private int i = inQueue;
    private final T[] randomizedArray;

    @SuppressWarnings("unchecked")
    public RandomizedQueueIterator() {
      randomizedArray = (T[]) new Object[inQueue];
      for (int j = 0; j < inQueue; j++) {
        randomizedArray[j] = queue[j];
      }
      shuffleArray(randomizedArray);
    }

    @Override
    public boolean hasNext() {
      return i > 0;
    }

    @Override
    public T next() {
      if (!hasNext())
        throw new NoSuchElementException();
      return randomizedArray[--i];
    }

    private void shuffleArray(T[] arr) {
      Random rand = new Random();
      for (int j = inQueue - 1; j > 0; j--) {
        int index = rand.nextInt(j + 1);
        T temp = arr[index];
        arr[index] = arr[j];
        arr[j] = temp;
      }
    }
  }
}
