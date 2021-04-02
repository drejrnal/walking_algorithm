package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2018/12/27 7:34 PM
 */
public class LongestSubArraySmallThanAim {
    public static int getLongestSubArray(int[] arr,int aim) {
        int len = arr.length;
        int[] minSum = new int[len];
        int[] minSumEnd = new int[len];
        minSum[len-1] = arr[len-1];
        minSumEnd[len-1] = len-1;
        for(int i = len-2; i >=0;i--){
            if (minSum[i+1] <= 0){
                minSum[i] = minSum[i+1]+arr[i];
                minSumEnd[i] = minSumEnd[i+1];
            }
            else{
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            }
        }
        //for test
        /*for (int i = 0;i<len;i++){
            System.out.println(minSum[i] + "--" +minSumEnd[i]);
        }*/
        int traverseSum = 0;
        int rightBorder = 0;
        int ans = 0;
        for (int i = 0; i< arr.length;i++){
            while(rightBorder < arr.length && traverseSum+minSum[rightBorder]<=aim){
                traverseSum = traverseSum + minSum[rightBorder];
                rightBorder = minSumEnd[rightBorder]+1;
            }
            ans = Math.max(ans,rightBorder - i);
            if (rightBorder == i) {
                rightBorder = i + 1;
            }
            else
                traverseSum = traverseSum - arr[i];
        }
        return ans;
    }
    public static int maxLength(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }
    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (getLongestSubArray(arr, k) != maxLength(arr, k)) {
                System.out.println("oops!");
            }
        }
    }
}
