package dynamicPrograme;


/**
 * @Author:毛翔宇
 * @Date 2018/11/30 6:43 AM
 */
public class DPLIS {
    public static int[] LIS(int[] arr) {
        int[] dp_Res = new int[arr.length];
        int[] end = new int[arr.length];
        end[0] = arr[0];
        dp_Res[0] = 1;
        int l;
        int r;
        int r_border = 0;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = r_border;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (end[mid] >= arr[i])
                    r = mid - 1;
                else
                    l = mid + 1;
            }
            r_border = Math.max(l,r_border);
            dp_Res[i] = l+1;
            end[l] = arr[i];
        }
        System.out.println(end[3]);
        return dp_Res;
    }
    public static int[] generateLIS(int[] arr,int[] dp){
        int index = 0;
        int len = 0;
        for (int i = 0;i<dp.length;i++){
            if (dp[i] > len){
                index = i;
                len = dp[i];
            }
        }
        //len最长递增子序列长度 index len长度对应的元素下标
        int[] lis = new int[len];
        lis[--len] = arr[index];
        for (int j = index;len>0;j--){
            if (arr[j] < arr[index] && dp[j] == dp[index]-1){
                lis[--len] = arr[j];
                index = j;
            }
        }
        return lis;
    }
    public static int[] lis2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int[] dp = LIS(arr);
        printArray(dp);
        return generateLIS(arr, dp);
    }
    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = { 2, 1, 5, 3, 6, 4, 8,  7 };
        printArray(arr);
        printArray(lis2(arr));

    }
}
