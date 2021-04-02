package treeProblem;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @Author:毛翔宇
 * @Date 2019/1/15 10:32 AM
 */
public class CATarjanQuery {


    public static class TreeNode{
        int value;
        TreeNode left;
        TreeNode right;
        TreeNode(int value){
            this.value = value;
        }
    }
    public static class Query{
        TreeNode n1;
        TreeNode n2;
        Query(TreeNode n1,TreeNode n2){
            this.n1 = n1;
            this.n2 = n2;
        }
    }
    //union-find structure
    public class unionSet{
        private  HashMap<TreeNode,TreeNode> peakNodeMap;
        private  HashMap<TreeNode,Integer> rankSetMap;
        public unionSet(){
            peakNodeMap = new HashMap<>();
            rankSetMap = new HashMap<>();
        }
        public void initialSet(TreeNode head){
            if (head == null)
                return;
            peakNodeMap.put(head,head);
            rankSetMap.put(head,1);
            initialSet(head.left);
            initialSet(head.right);
        }

        public  TreeNode find(TreeNode node){
            TreeNode peak = peakNodeMap.get(node);
            while (node != peak){
                node = peak;
                peak = peakNodeMap.get(node);
            }
            return node;
        }
        public void union(TreeNode n1, TreeNode n2){
            if (n1 == null || n2 == null)
                return;
            TreeNode p1 = find(n1);
            TreeNode p2 = find(n2);
            if (p1 == p2)
                return;
            else{
                int r1 = rankSetMap.get(p1),r2 = rankSetMap.get(p2);
                if (r1 >= r2){
                    peakNodeMap.put(p2,p1);
                    if (r1 == r2)
                        rankSetMap.put(p1,r1+1);
                }
                else{
                    peakNodeMap.put(p1,p2);
                }
            }
        }
    }

    private unionSet unionSet;
    private HashMap<TreeNode, LinkedList<TreeNode>> queryRecord ;
    private HashMap<TreeNode,LinkedList<Integer>> indexQueryRecord;
    private HashMap<TreeNode,TreeNode> ancestorMap;

    public CATarjanQuery(){
        this.unionSet = new unionSet();
        this.queryRecord = new HashMap<>();
        this.indexQueryRecord = new HashMap<>();
        this.ancestorMap = new HashMap<>();
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
    public void setAncestorMap(TreeNode head,TreeNode[] ans){
        if (head == null)
            return;
        setAncestorMap(head.left,ans);
        unionSet.union(head,head.left);
        ancestorMap.put(unionSet.find(head),head);
        setAncestorMap(head.right,ans);
        unionSet.union(head,head.right);
        ancestorMap.put(unionSet.find(head),head);
        LinkedList<TreeNode> queryList = queryRecord.get(head);
        LinkedList<Integer>  indexList = indexQueryRecord.get(head);
        int index;
        TreeNode queryNode,nodeFather;
        while(queryList != null && !queryList.isEmpty()){
            index = indexList.poll();
            queryNode = queryList.poll();
            nodeFather = unionSet.find(queryNode);
            if (ancestorMap.containsKey(nodeFather))
                ans[index] = ancestorMap.get(nodeFather);
        }
    }
    public TreeNode[] getQueryAnswer(TreeNode head,Query[] ques){
        TreeNode[] ans = new TreeNode[ques.length];
        setQueryRecord(ques);
        unionSet.initialSet(head);
        setAncestorMap(head,ans);
        return ans;
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



    //test union-set
   /* public void testUnionSet(TreeNode head){
        unionSet us = new unionSet();
        us.initialSet(head);
        for (TreeNode en: us.peakNodeMap.keySet())
            System.out.println(en.value+" "+us.peakNodeMap.get(en).value);
        mergeNodeOnSet(head.left,us);
        System.out.println("---After processing1----");
        for (TreeNode en: us.peakNodeMap.keySet())
            System.out.println(en.value+" "+us.peakNodeMap.get(en).value);
        mergeNodeOnSet(head.right,us);
        System.out.println("---After processing2----");
        for (TreeNode en: us.peakNodeMap.keySet())
            System.out.println(en.value+" "+us.peakNodeMap.get(en).value);
        us.union(head.left,head.right);
        System.out.println("---After processing3----");
        for (TreeNode en: us.peakNodeMap.keySet())
            System.out.println(en.value+" "+us.peakNodeMap.get(en).value);
    }
    public void mergeNodeOnSet(TreeNode root,unionSet us){
        if (root == null)
            return;
        mergeNodeOnSet(root.left,us);
        us.union(root,root.left);
        mergeNodeOnSet(root.right,us);
        us.union(root.right,root);

    }


    */
    //test common ancestor
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
        CATarjanQuery tjQuery = new CATarjanQuery();
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
