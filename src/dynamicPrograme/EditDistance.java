package dynamicPrograme;

/**
 * @Author:毛翔宇
 * @Date 2019/1/4 8:41 AM
 */
public class EditDistance {

    public static int ShortestEditDistance(String word1,String word2){
        char[] arr1 = word1.toCharArray();
        char[] arr2 = word2.toCharArray();
        int[] dp = new int[arr2.length+1];
        for (int i = 1;i<dp.length;i++)
            dp[i] = i;
        for (int i = 1;i<=arr1.length;i++){
            int pre = dp[0];
            dp[0] = i;
            for (int j = 1;j<=arr2.length;j++){
                int tmp = dp[j];
                dp[j] = (arr1[i-1] == arr2[j-1]?0:1) + pre;
                dp[j] = Math.min(dp[j],dp[j-1]+1);
                dp[j] = Math.min(dp[j],tmp+1);
                pre = tmp;
            }
        }
        return dp[arr2.length];
    }
    public static void main(String[] args){
        String str1 = "horse";
        String str2 = "ros";
        System.out.println(ShortestEditDistance(str1,str2));
    }
}
