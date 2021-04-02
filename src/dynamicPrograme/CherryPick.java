package dynamicPrograme;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2019-04-14 21:44
 */
public class CherryPick {
    public static int pickCherry(int[][] grid){
        int N = grid.length;
        int[][] cherryNum = new int[N][N];
        for (int[] row:cherryNum){
            Arrays.fill(row,Integer.MIN_VALUE);
        }
        cherryNum[0][0] = grid[0][0]==1?1:0;
        for (int t = 1;t < 2*N-1;t++){
            int[][] dp = new int[N][N];
            for (int[] row:dp)
                Arrays.fill(row,Integer.MIN_VALUE);
            for (int r1 = Math.max(0,t-N+1);r1<=Math.min(t,N-1);r1++){
                for (int r2 = Math.max(0,t-N+1);r2<=Math.min(t,N-1);r2++){
                   if( grid[r1][t-r1]<0 || grid[r2][t-r2]<0)
                        continue;
                    int val = grid[r1][t-r1];
                    val+=(r1 == r2)?0:grid[r2][t-r2];
                    for (int p1 = r1-1;p1<=r1;p1++){
                        for (int p2 = r2-1;p2<=r2;p2++){
                            if (p1 >= 0 && p2>=0) dp[r1][r2] = Math.max(cherryNum[p1][p2]+val,dp[r1][r2]);
                        }
                    }
                }
            }
        }
        return Math.max(cherryNum[N-1][N-1],0);
    }

    public static void main(String[] args){
        int[][] grid = {
                {0,1,-1},
                {1,0,-1},
                {1,1,1}
        };
        System.out.println(pickCherry(grid));
    }
}
