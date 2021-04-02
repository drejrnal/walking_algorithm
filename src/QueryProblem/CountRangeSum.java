package QueryProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/25 11:24 PM
 * 2019/1/26 leetcode 通过
 */
public class CountRangeSum {
    private TreapNode root;
    public CountRangeSum(){
        root = null;
    }
    public static class TreapNode{
        private double priority;
        private long value;
        private int size;
        private TreapNode left;
        private TreapNode right;
        public TreapNode(long _value){
            this.value = _value;
            this.priority = Math.random();
            this.left = null;
            this.right = null;
            this.size = 1;
        }
        public void setLeft(TreapNode ln){
            left = ln;
            calcuSize();
        }
        public void setRight(TreapNode rn){
            right = rn;
            calcuSize();
        }
        public void calcuSize(){
            size = 1;
            if (left != null)
                size += left.size;
            if (right != null)
                size += right.size;
        }
    }
    public TreapNode[] splitTree(TreapNode head,long value){
        if (head == null)
            return new TreapNode[]{null,null};
        if (head.value < value){
            TreapNode[] rs = splitTree(head.right,value);
            head.setRight(rs[0]);
            return new TreapNode[]{head,rs[1]};
        }
        TreapNode[] ls = splitTree(head.left,value);
        head.setLeft(ls[1]);
        return new TreapNode[]{ls[0],head};
    }
    public TreapNode addNode(TreapNode head,TreapNode tNode){
        if (head == null)
            return tNode;
        if (head.priority < tNode.priority){
            TreapNode[] tnPair = splitTree(head,tNode.value);
            tNode.setLeft(tnPair[0]);
            tNode.setRight(tnPair[1]);
            return tNode;
        }
        else if (head.value > tNode.value){
            head.setLeft(addNode(head.left,tNode));
        }
        else
            head.setRight(addNode(head.right,tNode));
        return head;
    }
    public int lessThanKey(TreapNode head,long value){
        if (head == null) return 0;
        if (head.value >= value)
            return lessThanKey(head.left,value);
        int size = head.left == null ? 0: head.left.size;
        return size + 1 + lessThanKey(head.right,value);
    }
    public int getRangeSumCount(int[] arr,int lower,int upper){
        long sum = 0;
        int res = 0;
        root = addNode(root,new TreapNode(0));
        for (int i = 0;i<arr.length;i++){
            sum += arr[i];
            int lb = lessThanKey(root,sum - upper);
            int rb = root.size - lessThanKey(root,sum - lower+1);
            res += i+1-lb-rb;
            root = addNode(root,new TreapNode(sum));
        }
        return res;
    }
    public static int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i)
            sums[i + 1] = sums[i] + nums[i];
        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1)
            return 0;
        int mid = (start + end) / 2;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);
        int j = mid, k = mid, t = mid;
        long[] cache = new long[end - start];
        for (int i = start, r = 0; i < mid; ++i, ++r) {
            while (k < end && sums[k] - sums[i] < lower)
                k++;
            while (j < end && sums[j] - sums[i] <= upper)
                j++;
            while (t < end && sums[t] < sums[i])
                cache[r++] = sums[t++];
            cache[r] = sums[i];
            count += j - k;
        }
        System.arraycopy(cache, 0, sums, start, t - start);
        return count;
    }
    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test

    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

    public static void main(String[] args) {

        int[] arr = {0,2,1,1,1};
        int len = 5;
        int varible = 3;
        //System.out.println(crs.getRangeSumCount(arr,0,2));
        for (int i = 0; i < 5; i++) {
            CountRangeSum crs = new CountRangeSum();
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = crs.getRangeSumCount(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }

    }


}
