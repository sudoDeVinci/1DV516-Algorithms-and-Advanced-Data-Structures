package src.Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Deque<T> implements Iterable<T> {
    
    /**
     * Nodes are the items within the deque.
     */
    private class Node {
        T value;
        Node next;
        Node prev;
    } 
    
    private Node first;
    private Node last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Get the size of the queue.
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Check if queue is empty.
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Add a non-null Item to the back of the queue.
     * @param item
     */
    public void addFirst(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot queue null item.");
        }

        /**
         * Create new node with new item. 
         */
        Node newNode = new Node();
        newNode.value = item;

        /**
         * New node's next points to the current first.
         */
        newNode.next = first;

        /**
         * If the queue is empty, the new node is also the last node.
         */
        if(isEmpty()) {
            last = newNode;
        } else {
            first.prev = newNode;
        }

        /**
         * Now set the 'first' variable to point to the new node. 
         */
        first = newNode;
        size++;
    }

    public void addLast(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot queue null item.");
        }

        /**
         * Create new node with new item. 
         */
        Node newNode = new Node();
        newNode.value = item;
 
        /**
         * The new node's prev points to the current last.
         */
        newNode.prev = last;

        /**
         * If the queue is empty, the new node is also the first node.
         */
        if (isEmpty()) {
            first = newNode;
        } else {
            last.next = newNode;
        }

        /**
         * Now set the 'last' variable to point to the new node. 
         */
        last = newNode;
        size++;


    }

    /**
     * Remove the first item from the list.
     * @return
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is currently empty.");
        }
        T item = first.value;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return item;
    }

    /**
     * Remove the last item from the queue.
     * @return
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is currently empty.");
        }
        T item = last.value;
        last = last.prev;
        size--;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    /**
     * Iterator to be able to iterate over the queue contents.
     */
    private class DequeIterator implements Iterator <T> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in queue");
            }

            T item = current.value;
            current = current.next;
            return item;
        }
    }

    /**
     * Iterator object to return publicly.
     */
    public Iterator<T> iterator() {
        return new DequeIterator();
    }


}