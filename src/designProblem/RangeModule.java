package designProblem;


import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Author:毛翔宇
 * @Date 2019-02-28 13:02
 */
public class RangeModule {

    private TreeMap<Integer,Integer> disjointInterval = new TreeMap<>();
    public void add(int left,int right){
        Integer upperBoundLeft = disjointInterval.floorKey(left);
        Integer upperBoundRight = disjointInterval.floorKey(right);
        if (upperBoundLeft == null && upperBoundRight == null)
            disjointInterval.put(left,right);
        else if (upperBoundLeft != null && disjointInterval.get(upperBoundLeft) >= left){
            int endian = Math.max(disjointInterval.get(upperBoundLeft),right);
            if (upperBoundRight!=null)
                endian = Math.max(endian,disjointInterval.get(upperBoundRight));
            disjointInterval.put(upperBoundLeft,endian);
        }
        else
            disjointInterval.put(left,Math.max(disjointInterval.get(upperBoundRight),right));
        SortedMap<Integer,Integer> sub = disjointInterval.subMap(left,false,right,true);
        Set<Integer> tmp = new TreeSet<>(sub.keySet());
        disjointInterval.keySet().removeAll(tmp);
    }
    public boolean query(int left,int right){
        Integer upperBoundLeft = disjointInterval.floorKey(left);
        if (upperBoundLeft!=null && disjointInterval.get(upperBoundLeft)>=right)
            return true;
        return false;
    }
    public void remove(int left,int right){
        Integer upperBoundLeft = disjointInterval.floorKey(left);
        Integer upperBoundRight = disjointInterval.floorKey(right);
        if (upperBoundRight != null && disjointInterval.get(upperBoundRight) > right){
            disjointInterval.put(right,disjointInterval.get(upperBoundRight));
        }
        if (upperBoundLeft != null && disjointInterval.get(upperBoundLeft) > left){
            disjointInterval.put(upperBoundLeft,left);
        }
        disjointInterval.subMap(left,true,right,false).clear();
    }
    public static void main(String[] args){
        RangeModule rm = new RangeModule();
        rm.add(6,8);
        rm.remove(7,8);
        rm.remove(8,9);
        rm.add(8,9);
        rm.remove(1,3);
        rm.add(1,8);
        System.out.println(rm.disjointInterval.keySet());
    }
}
