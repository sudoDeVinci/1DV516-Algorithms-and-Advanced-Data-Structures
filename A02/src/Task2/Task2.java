package src.Task2;

public class Task2 {
    public static void run() throws Exception {
        System.out.println("\nTest for starting size 0");
        try {
            DequeRand<Integer> __ = new DequeRand<>(0);
            __.size();
        } catch (Exception e) {
            System.out.println(e + "\n");
        }

        System.out.println("\nTest for deleting from empty queue");
        try {
            DequeRand<Integer> queue1 = new DequeRand<>(1);
            queue1.enqueue(3);
            queue1.dequeue();
            queue1.dequeue();
        } catch (Exception e) {
            System.out.println(e + "\n");
        }

        DequeRand<Integer> queue2 = new DequeRand<>(1);
        queue2.enqueue(1);
        queue2.enqueue(2);
        queue2.enqueue(3);
        queue2.enqueue(4);
        queue2.enqueue(5);
        queue2.enqueue(6);


        System.out.println("Iterator testing:");
        // Iterator test
        for (Object val : queue2) {
            System.out.println(val);
        }

        System.out.println("\nRemoving all values...");
        int size = queue2.size();
        for (int i = 0; i < size; i++) {
            System.out.println(queue2.dequeue());
        }
        System.out.println("Is queue empty? " + queue2.isEmpty());
    }

    public static void main(String[] args) {
        
        try {
            run();
        } catch (Exception e) {
            System.out.println(e + "\n");
        }

    }
}