package treeProblem;

/**
 * @Author:毛翔宇
 * @Date 2018/11/21 6:31 AM
 */


public class MaxTreePathSum {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int data) {
            this.val = data;
        }
    }

    static class ReturnType{
        int most;
        int sumVal;
        ReturnType(int m,int s){
            most = m;
            sumVal = s;
        }
    }
    public  static ReturnType mostRemote(TreeNode head){
        if (head == null)
            return new ReturnType(Integer.MIN_VALUE,0);
        int lm,rm,lh,rh;
        ReturnType leftInfo = mostRemote(head.left);
        lm = leftInfo.most;
        lh = leftInfo.sumVal;
        ReturnType rightInfo = mostRemote(head.right);
        rm = rightInfo.most;
        rh = rightInfo.sumVal;
        int curMost = Math.max(lm,rm);
        if(lh+rh+head.val > curMost)
            curMost = lh+rh+head.val;
        return new ReturnType(curMost,Math.max(lh,rh)+head.val);
    }
    
    public static void main(String[] args) {
        TreeNode head1 = new TreeNode(2);
        head1.left = new TreeNode(-1);
        head1.right = new TreeNode(-2);
        System.out.println();
    }
}

