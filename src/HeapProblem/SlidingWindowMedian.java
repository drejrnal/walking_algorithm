package HeapProblem;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Author:毛翔宇
 * @Date 2019-03-12 16:05
 * todo::仅超过25%
 */
public class SlidingWindowMedian {

    private TreeSet<Integer> lowerHalf;  //equivalent to the MaxHeap
    private TreeSet<Integer> higherHalf; //equivalent to the MinHeap
    public double[] medianSlidingWindow(int[] nums,int k){
        Comparator<Integer> bstComp = (a,b) -> nums[a]!=nums[b]?Integer.compare(nums[a],nums[b]):a-b;
        //Attention
        //Comparator<Integer> bstComp = (a,b) -> nums[a]!=nums[b]?nums[a]-nums[b]:a-b; may cause overflow
        lowerHalf = new TreeSet<>(bstComp.reversed());
        higherHalf = new TreeSet<>(bstComp);
        double[] res = new double[nums.length-k+1];
        Runnable rebalance = ()->{
            if (lowerHalf.size() == higherHalf.size()+2)
                higherHalf.add(lowerHalf.pollFirst());
            if (higherHalf.size() == lowerHalf.size()+2)
                lowerHalf.add(higherHalf.pollFirst());
        };
        for(int i = 0,j = 0;j < res.length;i++){
            if (i >= k){
                if (lowerHalf.size() == higherHalf.size())
                    res[j++] = ((double)nums[lowerHalf.first()]+nums[higherHalf.first()])/2;
                else if (lowerHalf.size() > higherHalf.size())
                    res[j++] = (double)nums[lowerHalf.first()];
                else
                    res[j++] = (double)nums[higherHalf.first()];
                if (!lowerHalf.remove(i-k)) higherHalf.remove(i-k);
                rebalance.run();
            }
            if (i < nums.length)
                addNumber(i,nums,rebalance);
        }
        return res;
    }

    public void addNumber(int index,int[] nums,Runnable runnable){
        if (lowerHalf.isEmpty()){
            lowerHalf.add(index);
            return;
        }
        if (nums[lowerHalf.first()] >= nums[index]){
            lowerHalf.add(index);
        }
        else{
            if (higherHalf.isEmpty()){
                higherHalf.add(index);
                return;
            }
            if (nums[higherHalf.first()] > nums[index])
                lowerHalf.add(index);
            else
                higherHalf.add(index);

        }
        runnable.run();
    }

    public void reBalance(){
        if (lowerHalf.size() == higherHalf.size()+2)
            higherHalf.add(lowerHalf.pollFirst());
        if (higherHalf.size() == lowerHalf.size()+2)
            lowerHalf.add(higherHalf.pollFirst());
    }

    public static void main(String[] args){
        int[] nums = {1,3,-1,-3,5,3,6,7};
        SlidingWindowMedian swm = new SlidingWindowMedian();
        double[] res = swm.medianSlidingWindow(nums,3);
        for (int i = 0;i<res.length;i++)
            System.out.println(res[i]);
    }

}
