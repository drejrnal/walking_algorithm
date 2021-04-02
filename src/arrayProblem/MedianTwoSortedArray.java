package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/1 3:40 PM
 */
public class MedianTwoSortedArray {

    public static double getUpMedianEqualLen(int s1,int e1,int[] arr1,int s2,int e2,int[] arr2) {
        int mid1 = 0;
        int mid2 = 0;
        int offset = 0;
        System.out.println(e2);
        while (s1 < e1 && s2 < e2) {
            mid1 = (s1 + e1) >> 1;
            mid2 = (s2 + e2) >> 1;
            offset = (e1 - s1+1)&1^1;
            if (arr1[mid1] == arr2[mid2])
                return arr1[mid1];
            if (arr1[mid1] > arr2[mid2]) {
                e1 = mid1;
                s2 = mid2 + offset;
            } else {
                s1 = mid1 + offset;
                e2 = mid2;
            }

        }
        return Math.min(arr1[s1],arr2[s2]);
    }
    public static double getMedian(int[] shorts,int[] longs,int k){
        int m = shorts.length;
        int n = longs.length;
        if(k > n){
            if (shorts[k - n - 1] > longs[n-1])
                return shorts[k-n-1];
            if (shorts[m-1] < longs[k - m -1])
                return longs[k-m-1];
            return getUpMedianEqualLen(k-n,m-1,shorts,k-m,n-1,longs);
        }
        else {
            if (longs[k-m-1]>shorts[m-1])
                return longs[k-m-1];
            else
                return getUpMedianEqualLen(0, m-1,shorts,k-m,k-1,longs);
        }
    }
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == nums2.length && nums1.length == 1)
            return (nums1[0]+nums2[0])/2.0;
        if(nums1.length>nums2.length)
            return findMedianPreCondition(nums2,nums1);
        else
            return findMedianPreCondition(nums1,nums2);
    }
    //asserting nums1.len<=nums2.len
    public static double findMedianPreCondition(int[] arr1, int[] arr2){
        int offset = (arr1.length + arr2.length) & 1 ^ 0;
        int k = ((arr1.length + arr2.length) >> 1) + offset;
        if(arr1.length == 0){
            if(offset == 1)
                return arr2[k-1];
            else
                return (arr2[k-1]+arr2[k])/2.0;
        }
        double upmedian, downmedian;
        if (k == arr1.length)
            upmedian = getUpMedianEqualLen(0, arr1.length - 1, arr1, 0, arr2.length-1, arr2);
        else
            upmedian = getMedian(arr1, arr2, k);
        if (offset == 1)
            return upmedian;
        else {
            downmedian = getMedian(arr1, arr2, k + 1);
            return (upmedian + downmedian) / 2.0;
        }
    }
    public static void main(String[] args) {
        int[] arr1 = {1};
        int[] arr2 = {1};
        System.out.println(findMedianSortedArrays(arr1,arr2));
    }
}
