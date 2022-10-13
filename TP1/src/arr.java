public class arr {
    public static void main(String[] args) {
        int arr1[] = {2, 4, 6, 1, 2};
        int arr2[] = {3, 1, 3, 5, 3};
        int tot = arr1.length+arr2.length;
        System.out.println(tot);
        int arr3[] = new int[tot];

        int j = 0;
        int k = 0;
        for (int i = 0; i < tot; i++) {
            if (arr1[j] <= arr2[k]) {
                arr3[i] = arr1[j];
                j++;
            } else {
                arr3[i] = arr2[k];
                k++;
            }
        }
    }
}
