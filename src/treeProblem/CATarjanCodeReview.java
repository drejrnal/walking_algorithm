package treeProblem;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author:毛翔宇
 * @Date 2019-04-27 16:28
 * tarjan脱机最小公共祖先问题 --算法导论 与1-15号版本不同的是去掉了unionSet中数据结构和AncestorMap
 * 时间复杂度O(N⍺(N)+M) M表示查询次数，⍺(N)表示inverse of Ackermann Function
 */
public class CATarjanCodeReview {
    private UF unionSet;
    private HashMap<TreeNode, LinkedList<TreeNode>> queryRecord ;
    private HashMap<TreeNode,LinkedList<Integer>> indexQueryRecord;
    public static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode ufParent;
        TreeNode ancestor;
        byte rank;
        boolean black;
        public TreeNode(int value){
            this.value = value;
        }
        public void setAncestor(TreeNode ancestor){
            this.ancestor = ancestor;
        }
        public void setBlack(){
            this.black = true;
        }
        public boolean getBlack(){
            return this.black;
        }
        public TreeNode getAncestor(){
            return this.ancestor;
        }
    }
    public static class Query{
        TreeNode n1;
        TreeNode n2;
        Query(TreeNode n1, TreeNode n2){
            this.n1 = n1;
            this.n2 = n2;
        }
    }
    public class UF{
        public void makeSet(TreeNode node){
            node.rank = 0;
            node.ufParent = node;
            node.black = false;
        }
        public TreeNode find(TreeNode p){
            while(p != p.ufParent) {
                p.ufParent = p.ufParent.ufParent;
                p = p.ufParent;
            }
            return p;
        }
        public void union(TreeNode p, TreeNode q){
            TreeNode proot = find(p);
            TreeNode qroot = find(q);
            if (proot == qroot)
                return;
            if (proot.rank > qroot.rank)
                qroot.ufParent = proot;
            else if (proot.rank < qroot.rank)
                proot.ufParent = qroot;
            else{
                proot.ufParent = qroot;
                qroot.rank +=1;
            }
        }
    }
    public CATarjanCodeReview(){
        this.unionSet = new UF();
        this.queryRecord = new HashMap<>();
        this.indexQueryRecord = new HashMap<>();
    }
    public void LCA(TreeNode head, TreeNode[] ans){
        unionSet.makeSet(head);
        unionSet.find(head).setAncestor(head);
        if (head.left != null) {
            LCA(head.left,ans);
            unionSet.union(head, head.left);
            unionSet.find(head.left).setAncestor(head);//一个集合中的节点设置祖先节点
        }
        if (head.right != null){
            LCA(head.right,ans);
            unionSet.union(head, head.right);
            unionSet.find(head.right).setAncestor(head);
        }
        head.setBlack();
        LinkedList<TreeNode> queryList = queryRecord.get(head);
        LinkedList<Integer>  indexList = indexQueryRecord.get(head);
        int index;
        TreeNode queryNode;
        while(queryList != null && !queryList.isEmpty()){
            index = indexList.poll();
            queryNode = queryList.poll();
            //算法导论练习21-3（1）证明 对每个查询队u-v下列代码只执行一次。
            if (queryNode.getBlack())
                ans[index] = unionSet.find(queryNode).getAncestor();
        }
    }
    public void setQueryRecord(Query[] queries){
        TreeNode tn1 = null;
        TreeNode tn2 = null;
        for (int i = 0;i<queries.length;i++){
            tn1 = queries[i].n1;
            tn2 = queries[i].n2;
            if (!queryRecord.containsKey(tn1)){
                queryRecord.put(tn1,new LinkedList<>());
                indexQueryRecord.put(tn1,new LinkedList<>());
            }
            if (!queryRecord.containsKey(tn2)){
                queryRecord.put(tn2,new LinkedList<>());
                indexQueryRecord.put(tn2,new LinkedList<>());
            }
            queryRecord.get(tn1).add(tn2);
            indexQueryRecord.get(tn1).add(i);
            queryRecord.get(tn2).add(tn1);
            indexQueryRecord.get(tn2).add(i);
        }
    }
    public TreeNode[] getQueryAnswer(TreeNode head, Query[] ques){
        TreeNode[] ans = new TreeNode[ques.length];
        setQueryRecord(ques);
        unionSet = new UF();
        //unionSet.initialSet(head);
        LCA(head,ans);
        return ans;
    }
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
    public static void main(String[] args){
        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.left.left = new TreeNode(4);
        head.left.right = new TreeNode(5);
        head.right.left = new TreeNode(6);
        head.right.right = new TreeNode(7);
        head.right.right.left = new TreeNode(8);
        printTree(head);
        System.out.println("===============");
        //CATarjanQuery tjq = new CATarjanQuery();
        //tjq.testUnionSet(head);
        Query[] qs = new Query[7];
        CATarjanCodeReview tjQuery = new CATarjanCodeReview();
        qs[0] = new Query(head.left.right, head.right.left);//1
        qs[1] = new Query(head.left.left, head.left);//2
        qs[2] = new Query(head.right.left, head.right.right.left);//3
        qs[3] = new Query(head.left.left, head.right.right);//1
        qs[4] = new Query(head.right.right, head.right.right.left);//7
        qs[5] = new Query(head, head);//1
        qs[6] = new Query(head.left, head.right.right.left);//1
        TreeNode[] ans = tjQuery.getQueryAnswer(head,qs);
        for (int i = 0; i != ans.length; i++) {
            System.out.println("o1 : " + qs[i].n1.value);
            System.out.println("o2 : " + qs[i].n2.value);
            System.out.println("ancestor : " + ans[i].value);
            System.out.println("===============");
        }
    }
}
