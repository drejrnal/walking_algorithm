package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/16 2:43 PM
 */
public class SuperEggDrop {
    //空间压缩技巧
    public static int getEggDropMove(int K, int N) {
        int[] preArray = new int[N+1];
        int[] curArray = new int[N+1];
        for (int i = 1;i<N+1;i++)
            curArray[i] = i;
        for (int i = 1;i!=K;i++){
            int[] tmp = preArray;
            preArray = curArray;
            curArray = tmp;
            for (int j = 1;j!=N+1;j++){
                int min = Integer.MAX_VALUE;
                for (int k = 1;k!=j+1;k++){
                    min = Math.min(min,Math.max(curArray[j-k],preArray[k-1]));
                }
                curArray[j] = min+1;
            }

        }
        return curArray[N];
    }

    //非空间压缩
    public static int dpEggDropMove(int K,int N){
        int[][] dp = new int[N+1][K+1];
        for (int i =1;i<=N;i++)
            dp[i][1] = i;
        for (int i = 1;i<=N;i++){
            for (int j = 2;j<=K;j++){
                int min = Integer.MAX_VALUE;
                for (int k = 1;k!=i+1;k++)
                    min = Math.min(min,Math.max(dp[k-1][j-1],dp[i-k][j]));
                dp[i][j] = min+1;
            }
        }
        return dp[N][K];
    }
    //四边形不等式优化
    public static int optimalEggDropMove(int K,int N){
        int[][] dp = new int[N+1][K+1];
        int[] cand = new int[K+1];
        for (int i =1;i<=N;i++) {
            dp[i][1] = i;
        }
        for (int i = 1;i<=K;i++){
            dp[1][i] = 1;
            cand[i] = 1;
        }
        for (int i= 2;i<=N;i++){
            for (int j = K;j>1;j--){
                int min = Integer.MAX_VALUE;
                int minPar = cand[j];
                int maxPar = j == K?(i+1)/2:cand[j+1];
                for (int k = minPar;k<=maxPar;k++){
                    int cur = Math.max(dp[k-1][j-1],dp[i-k][j]);
                    if (cur <= min){
                        min = cur;
                        cand[j] = k;
                    }
                    dp[i][j] = min+1;
                }
            }
        }
        return dp[N][K];
    }
    public static int solution5(int N, int K) {
        if (N < 1 || K < 1) {
            return 0;
        }
        int bsTimes = log2N(N) + 1;
        if (K >= bsTimes) {
            return bsTimes;
        }
        int[] dp = new int[K];
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= N) {
                    return res;
                }
            }
        }
    }

    public static int log2N(int n) {
        int res = -1;
        while (n != 0) {
            res++;
            n = n>>1;
        }
        return res;
    }
    public static int solution3(int nLevel, int kChess) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        /*if (kChess == 1) {
            return nLevel;
        }*/
        int[] preArr = new int[nLevel + 1];
        int[] curArr = new int[nLevel + 1];
        for (int i = 1; i != curArr.length; i++) {
            curArr[i] = i;
        }
        for (int i = 1; i != kChess; i++) {
            int[] tmp = preArr;
            preArr = curArr;
            curArr = tmp;
            for (int j = 1; j != curArr.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k != j + 1; k++) {
                    min = Math.min(min, Math.max(preArr[k - 1], curArr[j - k]));
                }
                curArr[j] = min + 1;

            }
        }
        return curArr[curArr.length - 1];
    }
    public static void main(String[] args){

        //System.out.println(solution3(2, 1));
        //System.out.println(getEggDropMove(1,2));

        //System.out.println("==============");


        //System.out.println(solution3(21, 2));
        System.out.println(getEggDropMove(2,105));
        System.out.println(dpEggDropMove(2,105));
        System.out.println(optimalEggDropMove(2,105));
        System.out.println(solution5(105,2));
        System.out.println("==============");
    }
}
