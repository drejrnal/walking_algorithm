package dynamicPrograme;

/**
 * @Author:毛翔宇
 * @Date 2019/1/5 9:37 PM
 * 删列造序 leetcode id:960
 */
public class DeleteColumnMakeSort {

    public static int minDeletionSize(String[] strs){
        int numbers = strs.length;
        int strLen = strs[0].length();
        int[] dp = new int[strLen];
        for (int i = 0;i<dp.length;i++)
            dp[i] = 1;
        char[][] matrix = new char[strLen][numbers];
        for (int i = 0;i<numbers;i++){
            char[] strArray = strs[i].toCharArray();
            for (int j = 0;j<strLen;j++){
                matrix[j][i] = strArray[j];
            }
        }
        for (int i = 0;i<strLen;i++){
            for (int j = 0; j< i;j++){
                if (isMore(matrix[j],matrix[i]) && dp[i] < dp[j] + 1)
                    dp[i] = dp[j] +1;
            }

        }
        int maxDpIndex=0;
        for (int i = 0;i<dp.length;i++){
            if (dp[i]>dp[maxDpIndex])
                maxDpIndex = i;
        }
        return strLen - dp[maxDpIndex];
    }

    public static boolean isMore(char[] front,char[] cur){
        int len = front.length;
        int i = 0;
        for (;i<len;i++){
            if (front[i] > cur[i])
                break;
        }
        if ( i == len)
            return true;
        return false;
    }

    public static void main(String[] args){
        String[] strs = {"abbba"};
        System.out.println(minDeletionSize(strs));
    }

}
