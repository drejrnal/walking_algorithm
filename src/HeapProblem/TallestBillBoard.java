package HeapProblem;

import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2019/1/8 2:23 PM
 */
public class TallestBillBoard {

    public static int getMaxSumFromEqualDisjointSet(int[] rods) {
        HashMap<Integer, Integer> diffToValue = new HashMap<>(), cur;
        diffToValue.put(0, 0);
        for (int x : rods) {
            cur = new HashMap(diffToValue);
            for (int diff : cur.keySet()) {
                int val = cur.get(diff);
                diffToValue.put(diff + x, Math.max(val, diffToValue.getOrDefault(diff + x, 0)));
                int val2 = diffToValue.getOrDefault(Math.abs(diff - x), 0);
                if (diff - x >= 0)
                    diffToValue.put(diff - x, Math.max(val2, val + x));
                else
                    diffToValue.put(x - diff, Math.max(val2, val + diff));
            }
        }
        return diffToValue.get(0);
    }

    public static int tallestBillboard(int[] rods) {

        HashMap<Integer,Integer> cache = new HashMap<>(),cur;
        cache.put(0,0);
        for(int insval:rods){
            cur = new HashMap<>(cache);
            for(int key:cur.keySet()){
                int pos1 = key + insval;
                int prev1 = cache.getOrDefault(pos1,0);
                int max = cur.getOrDefault(key,0);
                cache.put(pos1,Math.max(max+insval,prev1));
                int pos2 = Math.abs(key - insval);
                int prev2 = cache.getOrDefault(pos2,0);
                cache.put(pos2,Math.max(Math.max(max-key+insval,max),prev2));
            }
        }
        return cache.get(0);
    }

    public static void main(String[] args){
        int[] a= {61,45,43,54,40,53,55,47,51,59,42};
        int[] b= {1,2,3,4,5,6};
        int[] c = {175,145,180,156,151,132,131,150,154,144,137,128,156,161,150};
        System.out.println(tallestBillboard(c));
    }

}
