package arrayProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019/1/31 9:46 PM
 * leetcode id:315
 */
public class CountSmaller {
    /*
    树状数组方法
     */
    private List<Integer> cnt = new ArrayList<>();
    public static class FenwickTree{
        private int[] tree;
        public FenwickTree(int size){
            tree = new int[size+1];
        }
        public void update(int pos,int val){
            pos +=1;
            while(pos < tree.length){
                tree[pos] += val;
                pos += (pos & -pos);
            }
        }
        public int sum(int pos){
            pos ++;
            int ret = 0;
            while(pos >= 1){
                ret += tree[pos];
                pos = pos & (pos-1);
            }
            return ret;
        }
    }
    public List<Integer> getCountSmaller(int[] nums){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0;i<nums.length;i++) {
            min = (nums[i] < min) ? nums[i] : min;
            max = (nums[i] > max) ? nums[i] : max;
        }

        List<Integer> cnt = new ArrayList<>();
        FenwickTree bit = new FenwickTree(max - min+1);
        for (int i = nums.length-1;i>=0;i--) {
            cnt.add(bit.sum(nums[i] - min-1));
            bit.update(nums[i] - min, 1);
        }
        Collections.reverse(cnt);
        return cnt;
    }
    /*
    归并排序方法
     */
    private List<Integer> nums;
    private int[] index;
    public CountSmaller(int[] arr){
        nums = new ArrayList<>(arr.length);
        index = new int[arr.length];
        for (int i = 0;i<arr.length;i++) {
            index[i] = i;
            nums.add(0);
        }
    }
    public void getCountSmaller1(int[] arr, int lb, int rb){
        if (lb == rb)
            return;
        int mid = (lb + rb)/2;
        getCountSmaller1(arr,lb,mid);
        getCountSmaller1(arr,mid+1,rb);
        mergeCount(arr,lb,mid,rb);

    }
    public void mergeCount(int[] arr,int start,int mid, int end){
        int[] tmp = new int[end - start+1];
        int count = 0;
        int i = 0,j = start,k = mid+1;
        while (j <= mid && k <= end){
            if (arr[index[j]] <= arr[index[k]]) {
                tmp[i++] = index[j];
                nums.set(index[j],nums.get(index[j])+count);
                j++;
            }
            else{
                count ++;
                tmp[i++] = index[k];
                k++;
            }
        }
        while (j<= mid) {
            tmp[i++] = index[j];
            nums.set(index[j],nums.get(index[j])+count);
            j++;
        }
        while (k<= end)
            tmp[i++] = index[k++];
        for (int t = 0;t<=end-start;t++)
            index[start+t] = tmp[t];
    }
    public static void main(String[] args){
        int[] arr = {5,2,6,1};
        CountSmaller cs = new CountSmaller(arr);
        //cs.getCountSmaller1(arr,0,arr.length-1);
        List<Integer> e = cs.getCountSmaller(arr);
        for (int i =0;i<e.size();i++)
            System.out.println(e.get(i));
    }
}
