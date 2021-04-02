package treeProblem;

import java.util.HashMap;
import java.util.HashSet;

public class CommonAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    public static Node lowestAncestor1(Node head,Node descedent1,Node descedent2) {
        if (head == null || head == descedent1 || head == descedent2)
            return head;
        Node left = lowestAncestor1(head.left, descedent1, descedent2);
        Node right = lowestAncestor1(head.right, descedent1, descedent2);
        if (left != null && right != null)
            return head;
        return left != null ? left : right;
    }
    public static class Record1{
        private  HashMap<Node,Node> childToParent = new HashMap<>();
        public  void initMap(Node head){
            if (head != null)
                childToParent.put(head,null);
        }
        public  void setMap(Node head){
            if (head == null)
                return;
            if (head.left != null)
                childToParent.put(head.left,head);
            if (head.right != null)
                childToParent.put(head.right,head);
            setMap(head.left);
            setMap(head.right);
        }
        public  Node lowestAncestor2(Node head,Node descedent1,Node descedent2){
            HashSet<Node> ancestor = new HashSet<>();
            ancestor.add(head);//若两子代节点均为根结点的两个孩子，则下面的parent返回空，故此处将head添加
            Node parent = childToParent.get(descedent1);
            while(parent != null){
                ancestor.add(parent);
                parent = childToParent.get(parent);
            }
            parent = childToParent.get(descedent2);
            while(! ancestor.contains(parent))
                parent = childToParent.get(parent);
            return parent;
        }
    }
    public static class Record2{
        private HashMap<Node,HashMap<Node,Node>> pairToAncester;
        public void initMap(Node head){
            pairToAncester.put(head,new HashMap<>());
            initMap(head.left);
            initMap(head.right);
        }
        public void setMap(Node head){
            initMap(head);
            childPairParent(head,head.left);
            childPairParent(head,head.right);
            subTreeRecord(head);
            setMap(head.left);
            setMap(head.right);
        }
        public void childPairParent(Node head,Node children){
            if (children == null)
                return;
            pairToAncester.get(children).put(head,head);
            childPairParent(head,children.left);
            childPairParent(head,children.right);
        }
        public void subTreeRecord(Node head){
            if (head == null)
                return;
            rightTreeToLeft(head.left,head.right,head);
            subTreeRecord(head.left);
            subTreeRecord(head.right);
        }
        public void rightTreeToLeft(Node left,Node traverse,Node common){
            if (traverse == null)
                return;
            //pairToAncester.get(traverse).put(left,common);
            leftTreeToRight(left,traverse,common);
            rightTreeToLeft(left,traverse.left,common);
            rightTreeToLeft(left,traverse.right,common);

        }
        public void leftTreeToRight(Node traverse,Node right,Node common){
            if (traverse == null)
                return;
            pairToAncester.get(traverse).put(right,common);
            leftTreeToRight(traverse.left,right,common);
            leftTreeToRight(traverse.right,right,common);
        }
        public Node query(Node descedent1,Node descedent2 ){
            if (descedent1 == descedent2)
                return descedent1;
            if (pairToAncester.containsKey(descedent1))
                return pairToAncester.get(descedent1).get(descedent2);
            if (pairToAncester.containsKey(descedent2))
                return pairToAncester.get(descedent2).get(descedent1);
            return null;
        }
    }


}
