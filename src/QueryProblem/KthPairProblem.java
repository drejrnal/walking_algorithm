package QueryProblem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author:毛翔宇
 * @Date 2019-06-11 14:24 ID719
 * 2019-06-12 15:04 ID373
 * 2019-06-13 16:00 ID378
 */
public class KthPairProblem {
    private class Node{
        int id;
        int col;
        int value;
        private Node(int id,int col,int value){
            this.id = id;
            this.col = col;
            this.value = value;
        }
    }
    public int KthSmallestSumInMatrix(int[][] matrix, int k){
        PriorityQueue<Node> pq = new PriorityQueue<>(matrix.length,
                (a,b) -> a.value - b.value
        );
        for (int i = 0;i<matrix.length;i++)
            pq.add(new Node(i,0,matrix[i][0]));
        int cnt = 0;
        Node topNode = null;
        while(cnt < k ){
            topNode = pq.poll();
            int t_id = topNode.id;
            int t_col = topNode.col;
            if (t_col < matrix.length-1)
                pq.add(new Node(t_id,t_col+1,matrix[t_id][t_col+1]));
            cnt++;
        }
        return topNode != null ?topNode.value:0;
    }
    public int KthSmallest(int[][] matrix, int k){
        int lo = matrix[0][0], hi = matrix[matrix.length-1][matrix.length-1];
        while(lo < hi){
            int mid = lo + (hi - lo)/2;
            int count = 0, j =matrix.length-1;
            for (int i = 0;i<matrix.length;i++){
                while (j>=0 && matrix[i][j] > mid)
                    j--;
                count += j+1;
            }
            if (count >= k)
                hi = mid;
            else
                lo = mid+1;
        }
        return lo;
    }
    /*
    *返回数组中两两元素构成的pair距离最小的第k个
     */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int lo = 0, hi = nums[nums.length - 1] - nums[0];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for (int left = 0, right = 0; left < nums.length; left++) {
                while (right < nums.length && nums[right] - nums[left] <= mid)
                    right++;
                count += right - left - 1;
            }
            if (count >= k)
                hi = mid;
            else
                lo = mid + 1;

        }
        return lo;
    }
    /*
    *返回两数组构成的pair形成的和中最小的K个
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> re = new LinkedList<>();
        int lo = nums1[0] + nums2[0], hi = nums1[nums1.length - 1] + nums2[nums2.length - 1];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int count = 0;
            for (int i = 0, right = nums2.length - 1; i < nums1.length; i++) {
                while (right >= 0 && nums1[i] + nums2[right] > mid)
                    right--;
                count += right + 1;
            }
            if (count < k)
                lo = mid + 1;
            else
                hi = mid;
        }
        LinkedList<List<Integer>> equal = new LinkedList<>();
        for (int j = 0; j < nums2.length; j++) {
            for (int i = 0; i < nums1.length; i++) {
                if (re.size() < k && nums1[i] + nums2[j] < lo)
                    re.add(new LinkedList(Arrays.asList(nums1[i],nums2[j])));
                else if (nums1[i] + nums2[j] == lo)
                    equal.add(new LinkedList(Arrays.asList(nums1[i],nums2[j])));
                else
                    break;
            }
        }
        int remain = k - re.size();
        for (int i = 0;!equal.isEmpty() && i<remain;i++)
            re.add(equal.pollFirst());
        return re;
    }
    public static void main(String[] args) {
        KthPairProblem kpd = new KthPairProblem();
        int[] nums1 = {1,1,2}, nums2 = {1,2,3};
        int[][] matrix = {
                {1,5,9},
                {10,11,13},
                {12,13,15}
        };
        System.out.println(kpd.KthSmallestSumInMatrix(matrix,8));
        System.out.println(kpd.KthSmallest(matrix,8));
        //System.out.println(kpd.kSmallestPairs(nums1, nums2, 2));
    }
}
