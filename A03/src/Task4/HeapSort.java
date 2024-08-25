package Task4;

/**
 * Class to implement Heap Sort
 * O(log n), due to the recursive call stack
 */
public class HeapSort {


    static void heapify(Integer[] arr, Integer N, Integer max) {
        Integer root = max;
        Integer left = 2 * root + 1;
        Integer right = 2 * root + 2;

        if (left < N && arr[left] > arr[root]) root = left;
        if (right < N && arr[right] > arr[root]) root = right;
        if (!root.equals(max)) {
            Integer swap = arr[root];
            arr[root] = arr[max];
            arr[max] = swap;

            heapify(arr, N, root);
        }
    }

    public static void sort(Integer arr[]) {
        Integer n = arr.length;
        for (Integer i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (Integer i=n-1; i>=0; i--) {
            Integer temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    static void print(Integer arr[]) {
        Integer N = arr.length;
        for (Integer i = 0; i < N; ++i) System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void main(String args[])
    {
        Integer arr[] = { 12, 11, 13, -5, 42, 5, 6, 7 };
        
        // Function call
        HeapSort.sort(arr);

        System.out.println("Sorted array is");
        HeapSort.print(arr);
    }
}
