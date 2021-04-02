package treeProblem;



public class MostBst {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    public static class returnType{
        Node root;
        int size = 0;
        int maxValue;
        int minValue;
        public returnType(Node root,int maxValue,int minValue){
            this.root = root;
            this.maxValue = maxValue;
            this.minValue = minValue;
        }
    }
    public static int getWPL(Node head){
        int[] res = new int[1];
        wplCalculation(head,0,res);
        return res[0];
    }
    public static void wplCalculation(Node head,int deep,int[] res){
        if (head == null)
            return;
        wplCalculation(head.left,deep+1,res);
        wplCalculation(head.right,deep+1,res);
        if (head.left == null && head.right == null)
            res[0]+=head.value*deep;
    }
    /*
    采用树形dp方法解决 待leetcode检测
     */
    public static Node maxBST_dp(Node head){
        returnType result = process(head);
        return result.root;
    }
    public static returnType process(Node head){
        if (head == null){
            return new returnType(null,Integer.MIN_VALUE,Integer.MAX_VALUE);
        }
        int p,q;
        returnType leftSubtree = process(head.left);
        p = leftSubtree.maxValue ;
        //leftSubtree.minValue = Math.min(leftSubtree.minValue,head.value);
        returnType rightSubtree = process(head.right);
        //rightSubtree.maxValue = Math.max(rightSubtree.maxValue,head.value);
        q = rightSubtree.minValue ;
        leftSubtree.maxValue = Math.max(rightSubtree.maxValue,head.value);
        leftSubtree.minValue = Math.min(leftSubtree.minValue,head.value);
        rightSubtree.maxValue = Math.max(rightSubtree.maxValue,head.value);
        rightSubtree.minValue = Math.min(leftSubtree.minValue,head.value);
        if (leftSubtree.root == head.left && rightSubtree.root == head.right && p < head.value && q > head.value){
            returnType re = new returnType(head,rightSubtree.maxValue,leftSubtree.minValue);
            re.size = leftSubtree.size + rightSubtree.size +1;
            return re;
        }
        return leftSubtree.size >= rightSubtree.size ? leftSubtree: rightSubtree;
    }
    /*
    采用普通方式解决方法
     */
    public static Node maxBST(Node head){
        int[] record = new int[3];
        return postTraversal(head,record);
        //return record[0];
    }
    //record[1]表示以head开始最大搜索二叉树中的最大值，record[2]表示以head开始最大搜索二叉树中的最小值
    public static Node postTraversal(Node head,int[] record){
        if (head == null){
            record[0] = 0;
            record[1] = Integer.MIN_VALUE;
            record[2] = Integer.MAX_VALUE;
            return null;
        }
        int value = head.value;
        int lSubMin,lSubMax,rSubMin,rSubMax,lSize,rSize;
        Node l_Bst = postTraversal(head.left,record);
        lSize = record[0];
        lSubMax = record[1];
        lSubMin = record[2];
        Node r_Bst = postTraversal(head.right,record);
        rSize = record[0];
        rSubMax = record[1];
        rSubMin = record[2];
        record[1] = Math.max(value,rSubMax);
        record[2] = Math.min(value,lSubMin);
        if (head.left == l_Bst && head.right == r_Bst && value > lSubMax && value < rSubMin){
            record[0] = lSize + rSize +1;
            return head;
        }
        record[0] = lSize > rSize?lSize:rSize;
        return lSize >= rSize?l_Bst:r_Bst;
    }
    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
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
    public static void main(String[] args) {

        Node head = new Node(6);
        head.left = new Node(1);
        head.left.left = new Node(0);
        head.left.right = new Node(3);
        head.right = new Node(12);
        head.right.left = new Node(10);
        head.right.left.left = new Node(4);
        head.right.left.left.left = new Node(6);
        head.right.left.left.right = new Node(5);
        head.right.left.right = new Node(14);
        head.right.left.right.left = new Node(11);
        head.right.left.right.right = new Node(15);
        head.right.right = new Node(13);
        head.right.right.left = new Node(11);
        head.right.right.right = new Node(16);

        printTree(head);
        Node bst2 = maxBST(head);
        printTree(bst2);
        Node bst = maxBST_dp(head);
        printTree(bst);
        //int res = getWPL(head);
        //System.out.println(res);
    }
}
