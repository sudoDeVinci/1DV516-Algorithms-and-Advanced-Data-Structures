package Task5;
import Task3.InsertSort;
import Task4.HeapSort;
import java.util.Arrays;
public class QuickSort {

    public static enum Secondary {
        Insertion,
        Heap,
        None
    }

    public static void sort(Integer[] arr, Secondary secondary, Integer depth) {
        sort(arr, 0, arr.length - 2, secondary, depth);
    }


    public static void sort (Integer[] arr, Integer lo, Integer hi, Secondary secondary, Integer depth) {
        if (lo >= hi) return;

        if (depth == 0) {
            if (secondary == Secondary.Insertion) InsertSort.sort(arr, lo, hi + 1);
            else if (secondary == Secondary.Heap) HeapSort.sort(arr, lo, hi);
        } else {
            int pivotIndex = medianofThree(arr, lo, hi);
            int a = lo;
            int b = hi + 1;
            if (a == b) return;
            while (true) {
                do { a++; } while (arr[a] < arr[pivotIndex]);
                do { b--; } while (arr[b] > arr[pivotIndex]);
                
                if (a < b) swap(arr, a, b);
                else break;
            }

            swap(arr, a, hi - 1);
            sort(arr, lo, a - 1, secondary, depth - 1);
            sort(arr, a + 1, hi, secondary, depth - 1);
        }
    }

    public static void swap (Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int medianofThree(Integer[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);
        if (arr[low] > arr[mid]) swap(arr, low, mid);
        return mid;
    }


    public static void main(String[] args) {
        Integer[] arr = new Integer[20];
        for (int i = 0; i < 20; i++) {
            arr[i] = (int) (Math.random() * 20);
        }

        QuickSort.sort(arr, Secondary.Heap, 0);
        System.out.println(Arrays.toString(arr));
    }
}
