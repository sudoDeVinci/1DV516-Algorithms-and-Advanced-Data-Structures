package Task5;
import Task3.InsertSort;
import Task4.HeapSort;

public class QuickSort {

    public static enum Secondary {
        Insertion,
        Heap,
        None
    }

    public static void sort (Integer[] arr, int depth, Secondary secondary) {
        sort(arr, 0, arr.length - 1, depth, secondary);
    }

    private static void sort (Integer[] arr, int low, int high, int depth, Secondary secondary) {
        if (depth == 0 && secondary!= Secondary.None) {  
            if (secondary == Secondary.Insertion) InsertSort.sort(arr, low, high);
            else if (secondary == Secondary.Heap) HeapSort.sort(arr);
        } else {
            if (low < high) {
                int pivot = partition(arr, low, high);
                sort(arr, low, pivot - 1, depth - 1, secondary);
                sort(arr, pivot + 1, high, depth - 1, secondary);
            }
        }
    }

    public static void swap (Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int medianofThree(Integer[] arr, int low, int high) {
        int mid = low + (high - low) / 2;

        if (arr[low] > arr[mid]) swap(arr, low, mid);
        if (arr[low] > arr[high]) swap(arr, low, high);
        if (arr[mid] > arr[high]) swap(arr, mid, high);
        swap(arr, mid, high - 1);
        return arr[high - 1];
    }

    public static int partition(Integer[] arr, int low, int high) {
        int medianIndex = medianofThree(arr, low, high);
        int median = arr[medianIndex];
        swap(arr, medianIndex, high);
        int lower = low;

        for (int i = low; i < high; i++) {
            if (arr[i] <= median) {
                swap(arr, lower, i);
                lower++;
            }
        }

        swap(arr, lower, high);
        return lower;

    }
}
