package src.RandQueue;

import java.util.NoSuchElementException;
import java.util.Random;

public class RandQueue<T> implements Iterable<T> {
    private T[] array;
    private int size;
    private Random random;

    /**
     * Constructor to create empty queue.
     */
    public RandQueue() {
        this(1);
    }

    /**
     * Constructor to populate queue with array of items.
     * Suppress the warnings since generic array creation is not allowed.
     * @param items
     */
    @SuppressWarnings("unchecked")
    public RandQueue(T[] items) {
        array = (T[]) new Object[items.length];
        System.arraycopy(items,0, array,0, items.length);
        size = items.length;
        random = new Random();
    }

    /**
     * Create a new queue with an initial size.
     * Suppress the warnings since generic array creation is not allowed.
     * @param initialSize
     */
    @SuppressWarnings("unchecked")
    public RandQueue(int initialSize) {
        array = (T[]) new Object[initialSize];
        size = 0;
        random = new Random();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /**
     * Add item to the queue.
     * @param item
     */
    public void enqueue(T item) {
        if (size == array.length) {
            resize(array.length * 2);
        }
        array[size++] = item;
    }

    /**
     * Remove and return item from queue.
     * @return
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Can't remove item from empty queue.");
        }

        int randIndex = new Random().nextInt(size);
        T data = array[randIndex];

        array[randIndex] = array[size - 1];
        array[size - 1] = null;
        size--;

        if (size > 0 && size == array.length / 4)
        resize(array.length / 2);
        return data;
    }


    /**
     * Resize the current array to o
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        if (capacity > size) { // Only resize if the new capacity is greater than the current size
            T[] copy = (T[]) new Object[capacity];
            System.arraycopy(array, 0, copy, 0, size);
            array = copy;
    }

    
}

}
