package Task6;

public class MergeSortiterative {
    public static void merge (Integer[] arr, Integer lo, Integer mid, Integer hi) {
        // Sub-array sizes
        int size1 = mid - lo + 1;
        int size2 = hi - mid;

        // Create temp arrays
        Integer L[] = new Integer[size1];
        Integer R[] = new Integer[size2];

        // Copy data to temp arrays L[] and R[]
        for (int i = 0; i < size1; ++i) L[i] = arr[lo + i];
        for (int j = 0; j < size2; ++j) R[j] = arr[mid + 1 + j];

        // Merge the temp arrays back into arr[lo..hi]
        int i = 0, j = 0, k = lo;
        while (i < size1 && j < size2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of L[], if there are any
        while (i < size1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy the remaining elements of R[], if there are any
        while (j < size2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void sort(Integer[] arr) {
        int size = arr.length;
        for (int i = 1; i <= size - 1; i = 2 * i) {
            for (int j = 0; j < size - i; j += 2 * i) {
               int mid = Math.min(j + i - 1, size - 1);
               int right_end = Math.min(j + 2 * i - 1, size - 1);
               merge(arr, j, mid, right_end);
            }
        }
    }
}
