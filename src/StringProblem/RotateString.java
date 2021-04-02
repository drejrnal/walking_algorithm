package StringProblem;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2019-08-31 21:19
 */
public class RotateString {

    public int findRotateSteps(String ring, String key) {
        int[][] dp = new int[ring.length()][key.length()];
        for(int[] line : dp)    Arrays.fill(line, -1);

        return dfs(ring, 0, key, 0, dp) + key.length();
    }

    public int dfs(String ring, int rIndex, String key, int kIndex, int[][] dp){
        if(kIndex == key.length()) return 0;
        if(dp[rIndex][kIndex] != -1) return dp[rIndex][kIndex];

        char dest = key.charAt(kIndex);

        int nextIndex = ring.indexOf(dest);
        int sol = Integer.MAX_VALUE;
        do{
            int move = Math.min(Math.abs(rIndex - nextIndex), ring.length() - Math.abs(rIndex - nextIndex));
            int remain = dfs(ring, nextIndex, key, kIndex + 1, dp);
            sol = Math.min(sol, move + remain);
            nextIndex = ring.indexOf(dest, nextIndex + 1);
        }while(nextIndex != -1);
        dp[rIndex][kIndex] = sol;
        return sol;
    }
}
