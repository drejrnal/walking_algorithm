package dynamicPrograme;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2019-02-19 20:40
 */
public class PostofficeSelection {

    private int[][] distance;

    public PostofficeSelection(int[] coordination) {
        distance = new int[coordination.length+1][coordination.length+1];
        for (int i = 0; i < coordination.length; i++) {
            for (int j = i + 1; j < coordination.length; j++) {
                distance[i][j] = distance[i][j - 1] + coordination[j] - coordination[(i + j) / 2];
            }
        }
    }

    public int minDistance1(int[] arr, int num) {
        int[][] dp = new int[num][arr.length];
        for (int i = 0; i < dp[0].length; i++)
            dp[0][i] = distance[0][i];
        for (int i = 1; i < num; i++) {
            for (int j = i; j < dp[0].length; j++) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = 0; k < j; k++)
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + distance[k + 1][j]);
            }
        }
        return dp[num - 1][arr.length - 1];
    }

    //opitimized
    public int minDistance2(int[] arr, int num) {
        int[][] dp = new int[num][arr.length];
        int[][] s = new int[num][arr.length];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = distance[0][i];
            s[0][i] = 0;
        }
        for (int i = 1; i < num; i++) {
            for (int j = arr.length - 1; j >= i; j--) {
                int minK = s[i - 1][j];
                int maxK = j == arr.length - 1 ? arr.length - 1 : s[i][j + 1];
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = minK; k <= maxK; k++) {
                    int dis = dp[i-1][k] + distance[k + 1][j];
                    if (dis < dp[i][j]) {
                        dp[i][j] = dis;
                        s[i][j] = k;
                    }
                }
            }
        }
        return dp[num - 1][arr.length - 1];
    }


    // for test
    public static int[] getSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }



    public static void main(String[] args){
        int[] arr = { -2, -1, 0, 1, 2, 1000 };
        int num = 2;

        //System.out.println(pos.minDistance1(arr,num));

        int times = 100; // test time
        int len = 1000; // test array length
        int range = 2000; // every number in [0,range)
        int p = 50; // post office number
        long time1 = 0; // method1 all run time
        long time2 = 0;// method2 all run time
        long start = 0;
        long end = 0;
        int res1 = 0;
        int res2 = 0;
        for (int i = 0; i != times; i++) {
            arr = getSortedArray(len, range);
            PostofficeSelection pos = new PostofficeSelection(arr);
            start = System.currentTimeMillis();
            res1 = pos.minDistance1(arr, p);
            end = System.currentTimeMillis();
            time1 += end - start;
            start = System.currentTimeMillis();
            res2 = pos.minDistance2(arr, p);
            end = System.currentTimeMillis();
            time2 += end - start;
            if (res1 != res2) {
                printArray(arr);
                break;
            }
            if (i % 10 == 0) {
                System.out.print(". ");
            }
        }
        System.out.println();
        System.out.println("method1 all run time(ms): " + time1);
        System.out.println("method2 all run time(ms): " + time2);
    }


}
