package dynamicPrograme;

/**
 * @Author:毛翔宇
 * @Date 2018/12/30 8:19 PM
 */
public class MoneyConvert {

    public static int arbitaryCurrencyConvert(int[] currency,int money){
        int[][] dp = new int[currency.length][money+1];
        for (int row = 0;row<dp.length;row++)
            dp[row][0] = 1;
        int freq = 1;
        while (freq*currency[0]<=money){
            dp[0][freq*currency[0]] = 1;
            freq++;
        }
        for (int i = 1;i<dp.length;i++){
            for (int j = 1;j<dp[0].length;j++){
                dp[i][j] = dp[i-1][j];
                dp[i][j] += (j-currency[i] >= 0)?dp[i][j-currency[i]]:0;
            }
        }
        return dp[currency.length-1][money];
    }
    public static int spaceOptimalArbitaryConvert(int[] currency,int money){
        int[] dp = new int[money+1];
        for (int col = 0;col*currency[0]<=money;col++)
            dp[col*currency[0]] = 1;
        for (int i = 1;i<currency.length;i++){
            for (int j = 0;j<dp.length;j++) {
                dp[j] = dp[j] + (j - currency[i] >=0 ? dp[j - currency[i]] : 0);
            }
        }
        return dp[money];
    }
    public static int coins4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }
    public static void main(String[] args) {
        int[] coins = { 10, 5, 1, 25 };

        long start = 0;
        long end = 0;


        int aim = 20000;

        System.out.println("对比的方法");
        start = System.currentTimeMillis();
        System.out.println(coins4(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");


        System.out.println("二维动态表方法");
        start = System.currentTimeMillis();
        System.out.println(arbitaryCurrencyConvert(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");

        System.out.println("空间压缩技巧方法");
        start = System.currentTimeMillis();
        System.out.println(spaceOptimalArbitaryConvert(coins, aim));
        end = System.currentTimeMillis();
        System.out.println("cost time : " + (end - start) + "(ms)");
    }
}
