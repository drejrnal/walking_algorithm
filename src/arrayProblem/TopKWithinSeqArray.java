package arrayProblem;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2018/12/27 5:47 PM
 */
public class TopKWithinSeqArray {

    public static int getUpMedian(int[] arr1,int s1,int e1,int[] arr2,int s2,int e2){
        int mid1 = (s1 + e1)/2;
        int mid2 = (s2 + e2)/2;

        while(s1 < e1 && s2 < e2) {
            int offset = ((e1-s1+1)/2 & 1)^1;
            if (arr1[mid1] > arr2[mid2]) {
                e1 = mid1;
                s2 = mid2+offset;
            }
            else if(arr1[mid1] < arr2[mid2]) {
                s1 = mid1+offset;
                e2 = mid2;
            }
            else
                return arr1[mid1];
            mid1 = (s1 + e1)/2;
            mid2 = (s2 + e2)/2;
        }
        return Math.min(arr1[mid1],arr2[mid2]);
    }
    public static int getKthFromDoubleArray(int[] shorts,int[] longs,int k){
        //shorts数组长度为m,longs数组长度为n
        int m = shorts.length;
        int n = longs.length;
        if (k>m+n)
            return Integer.MIN_VALUE;
        if (k <= m)
            return getUpMedian(shorts,0,k-1,longs,0,k-1);
        if(k > n){
            if (shorts[k - n - 1] > longs[n-1])
                return shorts[k-n-1];
            if (shorts[m-1] < longs[k - m -1])
                return longs[k-m-1];
            return getUpMedian(shorts,k-n,m-1,longs,k-m,n-1);
        }
        if (shorts[m-1] < longs[k - m -1])
            return longs[k - m -1];
        else
            return getUpMedian(shorts,0,m-1,longs,k-m,k-1);
    }
    // For test, this method is inefficient but absolutely right
    public static int[] getSortedAllArray(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            throw new RuntimeException("Your arr is invalid!");
        }
        int[] arrAll = new int[arr1.length + arr2.length];
        int index = 0;
        for (int i = 0; i != arr1.length; i++) {
            arrAll[index++] = arr1[i];
        }
        for (int i = 0; i != arr2.length; i++) {
            arrAll[index++] = arr2[i];
        }
        Arrays.sort(arrAll);
        return arrAll;
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
        int len1 = 10;
        int len2 = 23;
        int maxValue1 = 20;
        int maxValue2 = 100;
        int[] arr1 = generateSortedArray(len1, maxValue1);
        int[] arr2 = generateSortedArray(len2, maxValue2);
        printArray(arr1);
        printArray(arr2);
        int[] sortedAll = getSortedAllArray(arr1, arr2);
        printArray(sortedAll);
        int kth = 12;
        System.out.println(getKthFromDoubleArray(arr1, arr2, kth));
        System.out.println(sortedAll[kth - 1]);
    }
}
