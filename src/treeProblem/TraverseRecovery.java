package treeProblem;


import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2019-03-15 09:04
 */
public class TraverseRecovery {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }


    public static TreeNode buildTree1(int[] preorder,int[] inorder){
        HashMap<Integer,Integer> index = new HashMap<>();
        for (int i = 0;i<inorder.length;i++)
            index.put(inorder[i],i);
        return process1(preorder,0,preorder.length-1,inorder,0,inorder.length-1,index);
    }
    public static TreeNode process1(int[] pre,int pStart,int pEnd,int[] in,int inStart,int inEnd,HashMap<Integer,Integer> map){
        if (pStart > pEnd )
            return null;
        TreeNode head = new TreeNode(pre[pStart]);
        int inOrderPos = map.get(pre[pStart]);
        head.left = process1(pre,pStart+1,pStart+inOrderPos-inStart,in,inStart,inOrderPos-1,map);
        head.right = process1(pre,pStart+inOrderPos-inStart+1,pEnd,in,inOrderPos+1,inEnd,map);
        return head;
    }

    public static TreeNode buildTree2(int[] posorder,int[] inorder){
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0;i<inorder.length;i++)
            map.put(inorder[i],i);
        return process2(posorder,0,posorder.length-1,inorder,0,inorder.length-1,map);
    }
    public static TreeNode process2(int[] pos,int posStart,int posEnd,int[] in,int inStart,int inEnd,HashMap<Integer,Integer> map){
        if (posStart > posEnd || inStart > inEnd)
            return null;
        TreeNode head = new TreeNode(pos[posEnd]);
        int index = map.get(pos[posEnd]);
        head.left = process2(pos,posStart,posStart+index-inStart-1,in,inStart,index-1,map);
        head.right = process2(pos,posStart+index-inStart,posEnd-1,in,index+1,inEnd,map);
        return head;
    }

    public static TreeNode buildTree3(int[] pre,int[] post){
        HashMap<Integer,Integer> index = new HashMap<>();
        for (int i = 0;i<post.length;i++)
            index.put(post[i],i);
        return process3(pre,0,pre.length-1,post,0,post.length-1,index);
    }
    public static TreeNode process3(int[] pre,int pStart,int pEnd,int[] pos,int posStart,int posEnd,HashMap<Integer,Integer> map){
        if (pStart > pEnd)
            return null;
        if (pStart == pEnd)
            return new TreeNode(pre[pStart]);
        TreeNode head = new TreeNode(pre[pStart]);
        int index = map.get(pre[pStart+1]);
        head.left = process3(pre,pStart+1,pStart+index-posStart+1,pos,posStart,index,map);
        head.right = process3(pre,pStart+2+index-posStart,pEnd,pos,index+1,posEnd-1,map);
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
        String val = to + head.val + to;
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
    public static void main(String[] args){
        int[] pre = {1,2,4,5,8,9,3,6,7};
        int[] in = {4,2,8,5,9,1,6,3,7};
        int[] pre1 = {1,2,4,5,3,6,7};
        int[] post = {4,5,2,6,7,3,1};
        //int[] pre1 = {1,2};
        //int[] post = {2,1};
        //printTree(buildTree1(pre,in));
        printTree(buildTree3(pre1,post));
    }
}
