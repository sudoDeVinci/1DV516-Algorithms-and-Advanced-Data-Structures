package Task3;

/**
 *  Insertion sort implementation
 *  Best case: O(n) , If the list is already sorted.
 *  Average case: O(n 2 )
 *  Worst case: O(n 2 )
 */
public class InsertSort {

    public static void sort (Integer[] arr) {
        InsertSort.sort(arr, 0, arr.length - 1);
    }

    public static void sort(Integer[] arr, Integer start, Integer end) {
        for (Integer i = start + 1; i <= end; i++) {
            Integer key = arr[i];
            Integer j = i - 1;
            while (j >= start && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void print(Integer arr[]) {
        Integer N = arr.length;
        for (Integer i = 0; i < N; ++i) System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        Integer arr[] = { 12, 11, 13, -5, 42, 5, 6, 7 };
        InsertSort.sort(arr);
        System.out.println("Sorted array is");
        InsertSort.print(arr);
    }
}
