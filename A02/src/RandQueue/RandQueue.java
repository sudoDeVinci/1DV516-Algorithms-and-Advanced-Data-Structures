package src.RandQueue;

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
     * @param items
     */
    public RandQueue(T[] items) {
        array = (T[]) new Object[initialSize];
        System.arraycopy(items,0, array,0, items.length);
        size = items.length;
        random = new Random();
    }

    
    public RandQueue(int initialSize) {
        array = (T[]) new Object[initialSize];
        size = 0;
        random = new Random();
    }
}
