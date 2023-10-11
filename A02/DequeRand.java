import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class DequeRand<T> implements Iterable<T> {
  private T[] queue;
  private int size;

  @SuppressWarnings("unchecked") // Generic array creation is not allowed in Java
  public DequeRand() { // but this *should* be safe
    queue = (T[]) new Object[1];
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(T data) {
    if (size == queue.length) {
      resize(2 * queue.length);
    }
    queue[size++] = data;
  }

  public T dequeue() {
    if (isEmpty())
      throw new NoSuchElementException();

    int randomIndex = new Random().nextInt(size);
    T data = queue[randomIndex];

    queue[randomIndex] = queue[size - 1];
    queue[size - 1] = null;
    size--;

    if (size > 0 && size == queue.length / 4)
      resize(queue.length / 2);
    return data;
  }

  @SuppressWarnings("unchecked")
  private void resize(int capacity) {
    T[] copy = (T[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      copy[i] = queue[i];
    }
    queue = copy;
  }

  @Override
  public Iterator<T> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<T> {
    private int i = size;
    private final T[] randomizedArray;

    @SuppressWarnings("unchecked")
    public RandomizedQueueIterator() {
      randomizedArray = (T[]) new Object[size];
      for (int j = 0; j < size; j++) {
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
      for (int j = size - 1; j > 0; j--) {
        int index = rand.nextInt(j + 1);
        T temp = arr[index];
        arr[index] = arr[j];
        arr[j] = temp;
      }
    }
  }
}
