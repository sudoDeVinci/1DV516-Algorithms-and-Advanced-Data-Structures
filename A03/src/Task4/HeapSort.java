package Task4;

/**
 * Class to implement Heap Sort
 * O(log n), due to the recursive call stack
 */
public class HeapSort {

    public static void sort(Integer arr[]) {
        sort(arr, 0, arr.length - 1);
    }


    public static void sort(Integer arr[], Integer start, Integer end) {
        Integer x;
        if (start <= 1) x = end / 2;
        else {
            if (start % 2 == 0) x = end / 2 + start;
            else x = end / 2 + start + 1;
        }

        while (x >= start) {
            swim_down(arr, start, end, x);
            x--;
        }

        while (end >= start) {
            swap(arr, start, end);
            end--;
            swim_down(arr, start, end, start);
        }
    }

    private static void swim_down(Integer[] arr, Integer start, Integer end, Integer x) {
        int parent = 1 - start;

        while (2 * x + parent <= end) {
            int ab = 2 * x + parent;

            if (ab < end && arr[ab] < arr[ab + 1]) ab++;

            if (arr[x] >= arr[ab]) break;

            swap(arr, x, ab);

            x = ab;
        }

    }

    static void print(Integer arr[]) {
        Integer N = arr.length;
        for (Integer i = 0; i < N; ++i) System.out.print(arr[i] + " ");
        System.out.println();
    }

    private static void swap(Integer[] arr, Integer x, Integer y) {
        int temp = arr[x];
        arr[x] = arr[y];
        arr[y] = temp;
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
