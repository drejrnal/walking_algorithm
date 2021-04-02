package arrayProblem;

public class Partition {

    public static int[] partition_refer(int[] arr, int l, int r, int p) {
        int less = l - 1;
        int more = r + 1;
        while (l < more) {
            if (arr[l] < p) {
                swap(arr, ++less, l++);
            } else if (arr[l] > p) {
                swap(arr, --more, l);
            } else {
                l++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    public static int[] partition(int[] array,int pivot){
        int len = array.length;
        int rBorder = len -1;
        int lBorder = -1;
        int i =0;
        while(i <= rBorder){
            if (array[i] < pivot){
                swap(array,++lBorder,i);
                i++;
            }
            else if(array[i] > pivot){
               swap(array,rBorder,i);
                rBorder--;
            }
            else
                i++;
        }
        return new int[]{lBorder,rBorder+1};
    }
    private static void swap(int[] arr,int i,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static int[] generateArray() {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 3);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] test = generateArray();
        int[] test2 = new int[test.length];
        for (int i = 0;i<test.length;i++)
            test2[i] = test[i];
        printArray(test);
        printArray(test2);
        int[] res1 = partition_refer(test, 0, test.length - 1, 1);
        int[] res2 = partition(test2,1);
        printArray(test);
        printArray(test2);
        System.out.println(res1[0]);
        System.out.println(res1[1]);
        System.out.println(res2[0]);
        System.out.println(res2[1]);

    }

}
