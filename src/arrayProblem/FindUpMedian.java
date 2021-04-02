package arrayProblem;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2019/1/23 6:02 PM
 * 看完编程珠玑第四章重新审视本题
 */
public class FindUpMedian {
    public static int getUpMedian(int[] arr1,int[] arr2){
        int s1 = 0,e1 = arr1.length-1,s2 = 0,e2 = arr2.length-1;
        int mid1 = (s1+e1)/2;
        int mid2 = (s2+e2)/2;
        while(s1 < e1 && s2 < e2){
            int offset = ((e1-s1+1)&1)^1;
            if (arr1[mid1] == arr2[mid2])
                return arr1[mid1];
            else if (arr1[mid1] < arr2[mid2]){
                s1 = mid1+offset;
                e2 = mid2;
            }
            else{
                e1 = mid1;
                s2 = mid2+offset;
            }
            mid1 = (s1+e1)/2;
            mid2 = (s2+e2)/2;
        }
        return Math.min(arr1[s1],arr2[s2]);
    }
    public static int findForTest(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int[] arrAll = new int[arr1.length + arr2.length];
        for (int i = 0; i != arr1.length; i++) {
            arrAll[i * 2] = arr1[i];
            arrAll[i * 2 + 1] = arr2[i];
        }
        Arrays.sort(arrAll);
        return arrAll[(arrAll.length / 2) - 1];
    }

    public static int[] generateSortedArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != len; i++) {
            res[i] = (int) (Math.random() * (maxValue + 1));
        }
        Arrays.sort(res);
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int maxValue1 = 20;
        int maxValue2 = 50;
        for (int i = 0;i<100;i++) {
            int[] arr1 = generateSortedArray(len, maxValue1);
            int[] arr2 = generateSortedArray(len, maxValue2);
            //printArray(arr1);
            //printArray(arr2);
            if (getUpMedian(arr1,arr2) == findForTest(arr1,arr2))
                System.out.println("Yeap");
        }
    }
}
