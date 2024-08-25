package Task6;

public class MergeSortRecursive {

       public static void merge (Integer[] arr, Integer lo, Integer mid, Integer hi, Integer size) {
        int i = lo;
        int j = mid + 1;
        int k = lo;

        Integer[] temp = new Integer[size];

        while (i <= mid && j <= hi) {
            if (arr[i] <= arr[j]) {
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }

            k++;
        }
        while (i <= mid) {
            temp[k] = arr[i];
            i++;
            k++;
        }

        while (j <= hi) {
            temp[k] = arr[j];
            j++;
            k++;
        }

        for (int el = lo; el <= hi; el++) arr[el] = temp[el];

    }

    public static void sort(Integer[] arr, Integer lo, Integer hi, Integer size) {
        if (lo < hi) {
            Integer mid = (hi + lo) / 2;

            sort(arr, lo, mid, size);
            sort(arr, mid + 1, hi, size);

            merge(arr, lo, mid, hi, size);
        }
    }

    public static void sort(Integer[] arr) {
        sort(arr, 0, arr.length-1, arr.length);
    }
}
