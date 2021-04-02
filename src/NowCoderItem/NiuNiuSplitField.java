package NowCoderItem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/10 4:38 PM
 * 2019/1/12 牛客-牛牛分田地代码通过
 */
import java.util.*;
public class NiuNiuSplitField {

    public static int[][] getMatrixSumTopDown(int[][] matrix){
        int row = matrix.length;
        int column = matrix[0].length;
        int[][] record = new int[row][column];
        record[0][0] = matrix[0][0];
        for (int i = 1;i<column;i++)
            record[0][i] = record[0][i-1] + matrix[0][i];
        for (int i = 1;i<row;i++)
            record[i][0] = record[i-1][0] + matrix[i][0];
        for (int i = 1;i<row;i++){
            for (int j = 1;j<column;j++){
                record[i][j] = record[i-1][j] + record[i][j-1] - record[i-1][j-1] + matrix[i][j];
            }
        }
        return record;
    }

    public static int areaSum(int sRow,int sCol,int eRow,int eCol,int[][] record){
        int left = 0,up = 0,lc = 0;
        if (sRow >= 1)
            up = record[sRow-1][eCol];
        if (sCol >= 1)
            left = record[eRow][sCol-1];
        if (sRow >=1 && sCol >=1)
            lc = record[sRow-1][sCol-1];
        return record[eRow][eCol] - left - up + lc;
    }

    public static int getMinAreaSum(int s,int e,int c1,int c2,int c3,int[][] record){
        int area1 = areaSum(s,0,e,c1,record);
        int area2 = areaSum(s,c1+1,e,c2,record);
        int area3 = areaSum(s,c2+1,e,c3,record);
        int area4 = areaSum(s,c3+1,e,record[0].length-1,record);
        return Math.min(Math.min(area1,area2),Math.min(area3,area4));
    }

    public static int[] tDMaxMatrixMinSumTD(int[][] record,int c1,int c2,int c3){
        int[] dp = new int[record.length];
        int split = 0;
        dp[1] = Math.min(getMinAreaSum(0,0,c1,c2,c3,record),getMinAreaSum(1,1,c1,c2,c3,record));
        for (int i = 2;i<dp.length;i++){
            int maxMins = Math.min(getMinAreaSum(split+1,i,c1,c2,c3,record),getMinAreaSum(0,split,c1,c2,c3,record));
            while (split < i-1){
                int next = Math.min(getMinAreaSum(split+2,i,c1,c2,c3,record),getMinAreaSum(0,split+1,c1,c2,c3,record));
                if (next < maxMins)
                    break;
                else{
                    maxMins = next;
                    split++;
                }
            }
            dp[i] = maxMins;
        }
        return dp;
    }
    public static int[] dTMaxMatrixMinSum(int[][] record,int c1,int c2,int c3){
        int[] dp = new int[record.length];
        int split = dp.length-1;
        dp[dp.length-2] = Math.min(getMinAreaSum(dp.length-2,dp.length-2,c1,c2,c3,record),getMinAreaSum(dp.length-1,dp.length-1,c1,c2,c3,record));
        for (int i = dp.length-3;i>=0;i--){
            int maxMins = Math.min(getMinAreaSum(i,split-1,c1,c2,c3,record),getMinAreaSum(split,dp.length-1,c1,c2,c3,record));
            while (split > i+1){
                int next = Math.min(getMinAreaSum(i,split-2,c1,c2,c3,record),getMinAreaSum(split-1,dp.length-1,c1,c2,c3,record));
                if (next < maxMins)
                    break;
                else{
                    maxMins = next;
                    split--;
                }
            }
            dp[i] = maxMins;
        }
        return dp;
    }
    public static int getBestSplit(int[][] record,int c1,int c2,int c3){
        int[] upDp = tDMaxMatrixMinSumTD(record,c1,c2,c3);
        int[] downDp = dTMaxMatrixMinSum(record,c1,c2,c3);
        int res = Integer.MIN_VALUE;
        for (int mid = 2;mid<record.length-1;mid++){
            res = Math.max(res,Math.min(upDp[mid-1],downDp[mid]));
        }
        return res;
    }
    public static int ans(int[][] matrix){
        if (matrix == null || matrix.length < 4 || matrix[0].length < 4) {
            return 0;
        }
        int[][] record = getMatrixSumTopDown(matrix);
        int col = matrix[0].length;
        int res = Integer.MIN_VALUE;
        for (int c1 = 0;c1<col-3;c1++){
            for (int c2 = c1+1;c2<col-2;c2++){
                for (int c3 = c2+1;c3<col-1;c3++)
                    res = Math.max(res,getBestSplit(record,c1,c2,c3));
            }
        }
        return res;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] matrix = new int[n][m];
            for (int i = 0; i < n; i++) {
                char[] chas = in.next().toCharArray();
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = chas[j] - '0';
                }
            }
            System.out.println(ans(matrix));
        }
        in.close();
    }
}
