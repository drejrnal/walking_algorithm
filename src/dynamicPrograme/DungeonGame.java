package dynamicPrograme;

/**
 * @Author:毛翔宇
 * @Date 2019-04-09 15:13
 */
public class DungeonGame {

    //min initial health to rescue the princess
    public static int minInitialHealth(int[][] dungeon){
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] health = new int[row][col];
        health[row-1][col-1] = dungeon[row-1][col-1] >= 0?1:1-dungeon[row-1][col-1];
        for (int j = col-2;j>=0;j--)
            health[row-1][j] = health[row-1][j+1] - dungeon[row-1][j]>0?health[row-1][j+1] - dungeon[row-1][j]:1;
        for (int i = row-2;i>=0;i--){
            int right = Integer.MAX_VALUE;
            int down;
            for (int j= col-1;j>=0;j--){
                if ( j+1 <col)
                    right = health[i][j+1] - dungeon[i][j] > 0?health[i][j+1] - dungeon[i][j]:1;
                down = health[i+1][j] - dungeon[i][j] > 0?health[i+1][j] - dungeon[i][j]:1;
                health[i][j] = Math.min(right,down);
            }
        }
        return health[0][0];
    }

    public static int spaceOptimal(int[][] dungeon){
        int space = Math.min(dungeon.length,dungeon[0].length);
        int iteration = Math.max(dungeon.length,dungeon[0].length);
        int[] health = new int[space];
        health[space-1] = dungeon[dungeon.length-1][dungeon[0].length-1]>0?1:1-dungeon[dungeon.length-1][dungeon[0].length-1];
        for (int j = space-2;j>=0;j--){
            int row = space == dungeon.length?dungeon.length-1:j;
            int col = space == dungeon.length?j:dungeon[0].length-1;
            health[j] = Math.max(health[j+1] - dungeon[row][col],1);
        }
        for (int i = iteration-2;i>=0;i--){
            int right = Integer.MAX_VALUE;
            int down;
            for (int j = space-1;j>=0;j--){
                int row = space == dungeon.length?i:j;
                int col = space == dungeon.length?j:i;
                if (j+1<space)
                    right = Math.max(health[j+1] - dungeon[row][col],1);
                down = Math.max(health[j]-dungeon[row][col],1);
                health[j] = Math.min(right,down);
            }
        }
        return health[0];
    }

    public static void main(String[] args){
        int[][] prison = {
                {0,0,0},
                {1,1,-1},

        };
        System.out.println(spaceOptimal(prison));
        System.out.println(minInitialHealth(prison));
    }
}
