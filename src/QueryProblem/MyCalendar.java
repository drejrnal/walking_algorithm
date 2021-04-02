package QueryProblem;

import java.util.TreeMap;

/**
 * @Author:毛翔宇
 * @Date 2019/1/27 2:19 PM
 */
public class MyCalendar {
    public static class TreeNode{
        int start;
        int end;
        int lazy;
        int total;
        int max;
        TreeNode left;
        TreeNode right;
        public TreeNode(int _start,int _end){
            this.start = _start;
            this.end = _end;
            this.lazy = 0;
            this.total = 0;
            this.max = 0;
        }
    }
    private TreeNode root;
    TreeMap<Integer,Integer> timeLine;
    public MyCalendar(){
        //timeLine = new TreeMap<>();
        root = new TreeNode(0,(1<<30)-1);
    }
    public int book(int start,int end){
        query(root,start,end);
        return root.total;
    }
    public void query(TreeNode head,int start,int end){
        if (head == null || head.start >= end || head.end <= start)
            return;
        if (head.start >= start && end>= head.end){
            head.lazy +=1;
            head.total+=1;
            return;
        }
        int mid = (head.start + head.end)>>1;
        if (isOverlap(start,end,head.start,mid)){
            if (head.left == null)
                head.left = new TreeNode(head.start,mid);
            query(head.left,start,end);
        }
        if (isOverlap(start,end,mid,head.end)){
            if (head.right == null)
                head.right = new TreeNode(mid,head.end);
            query(head.right,start,end);
        }
        head.total = head.lazy + Math.max((head.left==null?0:head.left.total),(head.right==null?0:head.right.total));
    }
    public boolean isOverlap(int start,int end,int NodeLeft,int NodeRight){
        if (NodeLeft >= end || NodeRight<= start)
            return false;
        return true;
    }

    public int book2(int start,int end){
        timeLine.put(start,timeLine.getOrDefault(start,0)+1);
        timeLine.put(end,timeLine.getOrDefault(end,0)-1);

        int ans = 0;
        int delta = 0;
        for (int entry:timeLine.values()){
            delta += entry;
            ans = Math.max(delta,ans);
        }
        return ans;
    }

    public int doubleQuery(TreeNode head, int start, int end){
        if (head == null || head.start >= end || head.end <= start)
            return 0;
        if (start <= head.start && head.end <= end) {

            return head.max;
        }
        pushDown(head);
        int lt = doubleQuery(head.left,start,end);
        int rt = doubleQuery(head.right,start,end);
        return Math.max(lt,rt);


    }
    public void update(TreeNode head,int start,int end,int val){
        if (head.start >= end || head.end <= start )
            return;
        if (start <= head.start && head.end <= end){
            head.lazy += val;
            head.max += val;
            return;
        }
        pushDown(head);
        update(head.left,start,end,val);
        update(head.right,start,end,val);
        head.max = Math.max(head.left.max,head.right.max);

    }
    public void pushDown(TreeNode head) {
        if (head.start < head.end) {
            int mid = (head.start + head.end) / 2;
            if (head.left == null) {
                head.left = new TreeNode(head.start, mid);
            }
            if (head.right == null) {
                head.right = new TreeNode(mid, head.end);
            }
            if (head.lazy > 0) {
                head.left.lazy += head.lazy;
                head.left.max += head.lazy;
                head.right.lazy += head.lazy;
                head.right.max += head.lazy;
                head.lazy = 0;
            }
        }
    }


    public boolean doubleBook(int start,int end){
        int num = doubleQuery(root,start,end);
        System.out.println(num);
        if (num >= 2)
            return false;
        update(root,start,end,1);
        //num = doubleQuery(root,start,end);
        return true;
    }
    public void printRange(TreeNode head){
        if (head == null)
            return;
        System.out.println(head.start + "------" + head.end);
        printRange(head.left);
        printRange(head.right);
    }
    public static void main(String[] args){
        MyCalendar myCalendar = new MyCalendar();

        System.out.println(myCalendar.doubleBook(26,35));
        System.out.println(myCalendar.doubleBook(26,32));
        System.out.println(myCalendar.doubleBook(25,32));
        System.out.println(myCalendar.doubleBook(18,26));
        //myCalendar.printRange(myCalendar.root);
        System.out.println(myCalendar.doubleBook(40,45));
        System.out.println(myCalendar.doubleBook(19,26));
        System.out.println(myCalendar.doubleBook(48,50));
        System.out.println(myCalendar.doubleBook(46,50));
        System.out.println(myCalendar.doubleBook(11,20));
    }
}
