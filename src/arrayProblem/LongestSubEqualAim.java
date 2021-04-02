package arrayProblem;

import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2018/12/28 7:30 PM
 */
public class LongestSubEqualAim {

    public static int getLongestEqualAim(int[] arr,int aim) {
        int sum = 0;
        int ans = 0;
        HashMap<Integer,Integer> valueToIndex = new HashMap<>();
        valueToIndex.put(0,-1);
        for(int i = 0;i<arr.length;i++){
            sum += arr[i];
            if (valueToIndex.containsKey(sum-aim)){
                ans = Math.max(ans,i - valueToIndex.get(sum-aim));
            }
            else{
                valueToIndex.put(sum,i);
            }
        }
        return ans;
    }
}
