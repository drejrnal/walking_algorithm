package arrayProblem;

import StringProblem.SuffixArrayGen;

import java.util.Arrays;

/**
 * @Author:毛翔宇
 * @Date 2019/1/20 3:37 PM
 */
public class CreateMaxNumber {

    public static int[][] getMaxIndex(int[] arr){
        int[][] dp = new int[arr.length][arr.length+1];
        int size = arr.length;
        for (int i = 1;i<=arr.length;i++){
            int maxindex = size - i;
            for (int j = size - i;j>=0;j--){
                if (arr[j] >= arr[maxindex])
                    maxindex = j;
                dp[j][i] = maxindex;
            }
        }
        return dp;
    }
    public static int[] getMaxSerialSubSeq(int[][] dp,int[] arr,int k){
        int[] res = new int[k];
        for(int resindex = 0,dpRow = 0;k>0;k--,resindex++){
            res[resindex] = arr[dp[dpRow][k]];
            dpRow = dp[dpRow][k]+1;
        }
        return res;
    }
    /*if(dp == null)
            return new int[]{};
    int len = k;
    int index = dp[0][len];
    int[] res = new int[k];
    int i = 0;
        while (i<k){
        res[i] =arr[index];
        if (index+1>=arr.length)
            break;
        index = dp[index+1][--len];
        i++;
    }
        return res;*/
    public static int[] conCatMaximumNum(int[] arr1,int[] arr2,int K){
        int len1 = arr1.length;
        int len2 = arr2.length;
        if (K<0 || K > len1+len2)
            return null;
        int[][] dp1 = getMaxIndex(arr1);
        int[][] dp2 = getMaxIndex(arr2);
        int lowerBound = Math.max(K - arr2.length,0);
        int upperBound = Math.min(arr1.length,K);
        int[] res = new int[K];
        for (int k = lowerBound;k<=upperBound;k++){
            int[] re1 = getMaxSerialSubSeq(dp1,arr1,k);
            int[] re2 = getMaxSerialSubSeq(dp2,arr2,K - k);
            int[] merge = mergeBasedSA(re1,re2);
            res = moreThan(merge,res)?merge:res;
        }
        return res;
    }
    public static int[] mergeBasedSA(int[] num1,int[] num2){
        int i = 0,j = 0,r = 0;
        int[] ans = new int[num1.length+num2.length];
        int[] s = transition(num1,num2);
        SuffixArrayGen suffixArrayGen = new SuffixArrayGen();
        int[] rank = suffixArrayGen.getRank(s);
        while (i < num1.length && j< num2.length){
            ans[r++] = rank[i] > rank[num1.length+j+1]?num1[i++]:num2[j++];
        }
        while (i < num1.length)
            ans[r++] = num1[i++];
        while (j < num2.length)
            ans[r++] = num2[j++];
        return ans;
    }
    public static int[] transition(int[] num1,int[] num2){
        int r = 0;
        int[] res = new int[num1.length+num2.length+1];
        for (int i = 0;i<num1.length;i++)
            res[r++] = num1[i]+2;
        res[r++] = 1;
        for (int i = 0;i<num2.length;i++)
            res[r++] = num2[i]+2;
       return res;
    }
    public static boolean moreThan(int[] current,int[] prev){
        int i = 0,j = 0;
        while (i<current.length && j<prev.length&&current[i] == prev[j]){
            i++;
            j++;
        }
        return (j == prev.length) || (current[i] > prev[j]);
    }
    public static void main(String[] args){

        int[] arr1 = {3,4,6,5};
        int[] arr2 = {9,1,2,5,8,3};
        /*int[][] dp = getMaxIndex(arr1);
        for (int i =0;i<arr1.length;i++) {
            for (int j = 0; j <= arr1.length; j++) {
                System.out.print(dp[i][j]+" ");
            }
            System.out.println();
        }
        int[] ans = getMaxSerialSubSeq(dp,arr1,2);
        for (int i = 0;i<ans.length;i++)
            System.out.print(ans[i]+" ");*/

        int[] res = conCatMaximumNum(arr1,arr2,5);
        for (int i =0;i<res.length;i++)
            System.out.print(res[i]+" ");
    }
}
