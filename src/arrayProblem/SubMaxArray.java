package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/14 1:44 PM
 */
public class SubMaxArray {

    //时间复杂度O(N) 空间复杂度O(1)
    /*
    * 数组遍历到当前位置i，变量sum记录的是遍历过程每个位置的累加和，
    * 若i位置处累加和<0，则sum = 0表示i位置之前的部分不可能作为最大子序列的一部分，
    * 若sum>0则i位置之前的部分有可能作为最大子序列一部分；
    * 用变量res记录遍历过程中的最大子序和。
    * */
    public static int getSubMaxSum(int[] arr){
        int sum = 0;
        int res = Integer.MIN_VALUE;
        for (int i = 0;i<arr.length;i++){
            sum += arr[i];
            res = Math.max(res,sum);
            sum = sum < 0?0:sum;
        }
        return res;
    }
    /*
    分治方法 复杂度O(NlgN)
     */
    public static int getSubMaxSum2(int[] arr){
        int start = 0,end = arr.length-1;
        return process(arr,start,end);
    }
    public static int process(int[] arr,int start,int end){
        if (start == end)
            return arr[start];
        int mid = (start+end)/2;
        int left = process(arr,start,mid);
        int right = process(arr,mid+1,end);
        int middle = includeMid(arr,start,mid,end);
        return Math.max(Math.max(left,right),middle);
    }
    public static int includeMid(int[] arr,int lB,int mid,int rB){
        int sum = 0;
        int max = Integer.MIN_VALUE;
        int left = mid,right = mid+1;
        while(left >= lB){
            if (arr[left]+sum >= max)
                max = arr[left]+sum;
            sum += arr[left];
            left--;
        }
        int leftMax = max;
        sum = 0;
        while(right <= rB){
            sum += arr[right];
            if (leftMax+sum >= max)
                max = leftMax+sum;
            right++;
        }
        return max;
    }


    public static int maxProductSubArray(int[] arr){
        int minPrev = arr[0],maxPrev = arr[0];
        int max = arr[0];
        for (int i = 1;i< arr.length;i++){
            int pos1 = minPrev * arr[i];
            int pos2 = maxPrev * arr[i];
            minPrev =Math.min(Math.min(pos1,pos2),arr[i]);
            maxPrev =Math.max(Math.max(pos1,pos2),arr[i]);
            max = Math.max(max,maxPrev);
        }
        return max;
    }



    public static int[] generateArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i != size; i++) {
            result[i] = (int) (Math.random() * 11) - 5;
        }
        return result;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args){
        double[] arr = {2,-5,-2,-4,3};

        /*
        for (int i = 1;i<=1000;i++){
            int[] arr = generateArray(10);
            if (maxProductSubArray(arr) != maxProductSubArray2(arr))
                System.out.println("Oops");
        }
         */
    }
}