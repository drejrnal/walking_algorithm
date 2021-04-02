package treeProblem;

import java.util.Stack;

/**
 * @Author:毛翔宇
 * @Date 2019-03-04 22:20
 * leetcode 99只是值的交换，不涉及节点结构的交换,本题recoverBST方法实现的是结构调整
 */
public class BSTRecovery {

    public static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.value = val;
        }
    }
    TreeNode err1 = null;
    TreeNode err2 = null;
    /*
    基于普通中序遍历交换值的变换
     */
    public void recoverTree(TreeNode head) {
        TreeNode[] par = new TreeNode[3];
        inOrder(head,par);
        int val = par[1].value;
        par[1].value = par[2].value;
        par[2].value = val;
    }
    public void inOrder(TreeNode head,TreeNode[] par){
        if( head == null )
            return;
        inOrder(head.left,par);
        if(par[0] != null && par[0].value > head.value){
            par[2] = head;
            par[1] = (par[1]== null)?par[0]:par[1];
        }
        par[0] = head;
        inOrder(head.right,par);
    }
    /*
    基于Morris遍历的交换值得变换
     */
    public void recoverUsingMorris(TreeNode head){
        TreeNode cur1 = head,cur2 = null,pre = null;
        while(cur1 != null){
            cur2 = cur1.left;
            if (cur2 != null){
                while(cur2.right != null && cur2.right != cur1){
                    cur2 = cur2.right;
                }
                if (cur2.right == null){
                    cur2.right = cur1;
                    cur1 = cur1.left;
                }
                else {
                    if (cur1.value < cur2.value ){
                        err1 = err1 == null ? cur2:err1;
                        err2 = cur1;
                    }
                    pre = cur1;
                    cur2.right = null;
                    cur1 = cur1.right;
                }
            }
            else{
                if (pre != null && pre.value > cur1.value){
                    err1 = err1 == null ? pre:err1;
                    err2 = cur1;
                }
                pre = cur1;
                cur1 = cur1.right;
            }
        }
    }

    public static TreeNode[] findErrorBSTNode(TreeNode head) {
        TreeNode[] errors = new TreeNode[2];
        Stack<TreeNode> record = new Stack<>();
        record.push(head);
        TreeNode peek = record.peek();
        TreeNode pre = null;
        while (!record.isEmpty()) {
            if (peek != null) {
                if (peek.left != null)
                    record.push(peek.left);
                peek = peek.left;
            } else {
                TreeNode tmp = record.pop();
                //System.out.println(tmp.value);
                if(pre != null && tmp.value < pre.value){
                    errors[0] = (errors[0] == null)?pre:errors[0];
                    errors[1] = tmp;
                }
                if (tmp.right != null)
                    record.push(tmp.right);
                peek = tmp.right;
                pre = tmp;
            }
        }
        return errors;
    }

    public static TreeNode[] findErrParent(TreeNode head, TreeNode error1, TreeNode error2){
        TreeNode[] errParents = new TreeNode[2];
        Stack<TreeNode> record = new Stack<>();
        while (head != null || !record.isEmpty()){
            if (head != null){
                record.push(head);
                head = head.left;
            }
            else{
                TreeNode tmp = record.pop();
                if (tmp.left == error1 || tmp.right == error1)
                    errParents[0] = tmp;
                if (tmp.left == error2 || tmp.right == error2)
                    errParents[1] = tmp;
                head = tmp.right;
            }
        }
        return errParents;
    }

    public static TreeNode recoverBST(TreeNode head){
        TreeNode[] errors = findErrorBSTNode(head);
        TreeNode[] errParent = findErrParent(head,errors[0],errors[1]);
        TreeNode e1P = errParent[0];
        TreeNode e2P = errParent[1];
        TreeNode e1L = errors[0].left;
        TreeNode e1R = errors[0].right;

        if (e2P != errors[0])
            head = transplant(head,errors[1],errors[0],e2P);
        if (e1P != errors[1])
            head = transplant(head,errors[0],errors[1],e1P);

        if (e1P == errors[1])
            errors[0].left = errors[1];
        else
            errors[0].left = errors[1].left;
        errors[0].right = errors[1].right;

        if (e2P == errors[0])
            errors[1].right = errors[0];
        else
            errors[1].right = e1R;
        errors[1].left = e1L;
        return head;
    }

    //origin.parent != replace
    public static TreeNode transplant(TreeNode head, TreeNode origin, TreeNode replace, TreeNode oriP){
        if (oriP == null)
            head = replace;
        else if (oriP.left == origin)
            oriP.left = replace;
        else
            oriP.right = replace;
        return head;
    }


    // for test -- print tree
    public static void printTree(TreeNode head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(TreeNode head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    // for test
    public static boolean isBST(TreeNode head) {
        if (head == null) {
            return false;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode pre = null;
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                if (pre != null && pre.value > head.value) {
                    return false;
                }
                pre = head;
                head = head.right;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // 1, 7 -> e1, 5 -> e2
        System.out.println("situation 1");
        TreeNode head1 = new TreeNode(7);
        head1.left = new TreeNode(3);
        head1.right = new TreeNode(5);
        head1.left.left = new TreeNode(2);
        head1.left.right = new TreeNode(4);
        head1.right.left = new TreeNode(6);
        head1.right.right = new TreeNode(8);
        head1.left.left.left = new TreeNode(1);
        printTree(head1);
        System.out.println(isBST(head1));
        TreeNode res1 = recoverBST(head1);
        printTree(res1);
        System.out.println(isBST(res1));

        // 2, 6 -> e1, 5 -> e2
        System.out.println("situation 2");
        TreeNode head2 = new TreeNode(6);
        head2.left = new TreeNode(3);
        head2.right = new TreeNode(7);
        head2.left.left = new TreeNode(2);
        head2.left.right = new TreeNode(4);
        head2.right.left = new TreeNode(5);
        head2.right.right = new TreeNode(8);
        head2.left.left.left = new TreeNode(1);
        printTree(head2);
        System.out.println(isBST(head2));
        TreeNode res2 = recoverBST(head2);
        printTree(res2);
        System.out.println(isBST(res2));

        // 3, 8 -> e1, 5 -> e2
        System.out.println("situation 3");
        TreeNode head3 = new TreeNode(8);
        head3.left = new TreeNode(3);
        head3.right = new TreeNode(7);
        head3.left.left = new TreeNode(2);
        head3.left.right = new TreeNode(4);
        head3.right.left = new TreeNode(6);
        head3.right.right = new TreeNode(5);
        head3.left.left.left = new TreeNode(1);
        printTree(head3);
        System.out.println(isBST(head3));
        TreeNode res3 = recoverBST(head3);
        printTree(res3);
        System.out.println(isBST(res3));

        // 4, 5 -> e1, 3 -> e2
        System.out.println("situation 4");
        TreeNode head4 = new TreeNode(3);
        head4.left = new TreeNode(5);
        head4.right = new TreeNode(7);
        head4.left.left = new TreeNode(2);
        head4.left.right = new TreeNode(4);
        head4.right.left = new TreeNode(6);
        head4.right.right = new TreeNode(8);
        head4.left.left.left = new TreeNode(1);
        printTree(head4);
        System.out.println(isBST(head4));
        TreeNode res4 = recoverBST(head4);
        printTree(res4);
        System.out.println(isBST(res4));

        // 5, 5 -> e1, 2 -> e2
        System.out.println("situation 5");
        TreeNode head5 = new TreeNode(2);
        head5.left = new TreeNode(3);
        head5.right = new TreeNode(7);
        head5.left.left = new TreeNode(5);
        head5.left.right = new TreeNode(4);
        head5.right.left = new TreeNode(6);
        head5.right.right = new TreeNode(8);
        head5.left.left.left = new TreeNode(1);
        printTree(head5);
        System.out.println(isBST(head5));
        TreeNode res5 = recoverBST(head5);
        printTree(res5);
        System.out.println(isBST(res5));

        // 6, 5 -> e1, 4 -> e2
        System.out.println("situation 6");
        TreeNode head6 = new TreeNode(4);
        head6.left = new TreeNode(3);
        head6.right = new TreeNode(7);
        head6.left.left = new TreeNode(2);
        head6.left.right = new TreeNode(5);
        head6.right.left = new TreeNode(6);
        head6.right.right = new TreeNode(8);
        head6.left.left.left = new TreeNode(1);
        printTree(head6);
        System.out.println(isBST(head6));
        TreeNode res6 = recoverBST(head6);
        printTree(res6);
        System.out.println(isBST(res6));

        // 7, 4 -> e1, 3 -> e2
        System.out.println("situation 7");
        TreeNode head7 = new TreeNode(5);
        head7.left = new TreeNode(4);
        head7.right = new TreeNode(7);
        head7.left.left = new TreeNode(2);
        head7.left.right = new TreeNode(3);
        head7.right.left = new TreeNode(6);
        head7.right.right = new TreeNode(8);
        head7.left.left.left = new TreeNode(1);
        printTree(head7);
        System.out.println(isBST(head7));
        TreeNode res7 = recoverBST(head7);
        printTree(res7);
        System.out.println(isBST(res7));

        // 8, 8 -> e1, 7 -> e2
        System.out.println("situation 8");
        TreeNode head8 = new TreeNode(5);
        head8.left = new TreeNode(3);
        head8.right = new TreeNode(8);
        head8.left.left = new TreeNode(2);
        head8.left.right = new TreeNode(4);
        head8.right.left = new TreeNode(6);
        head8.right.right = new TreeNode(7);
        head8.left.left.left = new TreeNode(1);
        printTree(head8);
        System.out.println(isBST(head8));
        TreeNode res8 = recoverBST(head8);
        printTree(res8);
        System.out.println(isBST(res8));

        // 9, 3 -> e1, 2 -> e2
        System.out.println("situation 9");
        TreeNode head9 = new TreeNode(5);
        head9.left = new TreeNode(2);
        head9.right = new TreeNode(7);
        head9.left.left = new TreeNode(3);
        head9.left.right = new TreeNode(4);
        head9.right.left = new TreeNode(6);
        head9.right.right = new TreeNode(8);
        head9.left.left.left = new TreeNode(1);
        printTree(head9);
        System.out.println(isBST(head9));
        TreeNode res9 = recoverBST(head9);
        printTree(res9);
        System.out.println(isBST(res9));

        // 10, 7 -> e1, 6 -> e2
        System.out.println("situation 10");
        TreeNode head10 = new TreeNode(5);
        head10.left = new TreeNode(3);
        head10.right = new TreeNode(6);
        head10.left.left = new TreeNode(2);
        head10.left.right = new TreeNode(4);
        head10.right.left = new TreeNode(7);
        head10.right.right = new TreeNode(8);
        head10.left.left.left = new TreeNode(1);
        printTree(head10);
        System.out.println(isBST(head10));
        TreeNode res10 = recoverBST(head10);
        printTree(res10);
        System.out.println(isBST(res10));

        // 11, 6 -> e1, 2 -> e2
        System.out.println("situation 11");
        TreeNode head11 = new TreeNode(5);
        head11.left = new TreeNode(3);
        head11.right = new TreeNode(7);
        head11.left.left = new TreeNode(6);
        head11.left.right = new TreeNode(4);
        head11.right.left = new TreeNode(2);
        head11.right.right = new TreeNode(8);
        head11.left.left.left = new TreeNode(1);
        printTree(head11);
        System.out.println(isBST(head11));
        TreeNode res11 = recoverBST(head11);
        printTree(res11);
        System.out.println(isBST(res11));

        // 12, 8 -> e1, 2 -> e2
        System.out.println("situation 12");
        TreeNode head12 = new TreeNode(5);
        head12.left = new TreeNode(3);
        head12.right = new TreeNode(7);
        head12.left.left = new TreeNode(8);
        head12.left.right = new TreeNode(4);
        head12.right.left = new TreeNode(6);
        head12.right.right = new TreeNode(2);
        head12.left.left.left = new TreeNode(1);
        printTree(head12);
        System.out.println(isBST(head12));
        TreeNode res12 = recoverBST(head12);
        printTree(res12);
        System.out.println(isBST(res12));

        // 13, 6 -> e1, 4 -> e2
        System.out.println("situation 13");
        TreeNode head13 = new TreeNode(5);
        head13.left = new TreeNode(3);
        head13.right = new TreeNode(7);
        head13.left.left = new TreeNode(2);
        head13.left.right = new TreeNode(6);
        head13.right.left = new TreeNode(4);
        head13.right.right = new TreeNode(8);
        head13.left.left.left = new TreeNode(1);
        printTree(head13);
        System.out.println(isBST(head13));
        TreeNode res13 = recoverBST(head13);
        printTree(res13);
        System.out.println(isBST(res13));

        // 14, 8 -> e1, 4 -> e2
        System.out.println("situation 14");
        TreeNode head14 = new TreeNode(5);
        head14.left = new TreeNode(3);
        head14.right = new TreeNode(7);
        head14.left.left = new TreeNode(2);
        head14.left.right = new TreeNode(8);
        head14.right.left = new TreeNode(6);
        head14.right.right = new TreeNode(4);
        head14.left.left.left = new TreeNode(1);
        printTree(head14);
        System.out.println(isBST(head14));
        TreeNode res14 = recoverBST(head14);
        printTree(res14);
        System.out.println(isBST(res14));

    }
}
