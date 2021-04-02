package treeProblem;

import java.util.Stack;

public class PreInPostTraverse {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    public static void preTraverse(Node head){
        if (head == null){
            return;
        }
        Stack<Node> preOrderStack = new Stack<>();
        preOrderStack.add(head);
        while (!preOrderStack.isEmpty()){
            Node peek = preOrderStack.pop();
            System.out.println(peek.value);
            if (head.right!=null)
                preOrderStack.push(head.right);
            if (head.left!=null)
                preOrderStack.push(head.left);
        }
    }
    public static void inOrder(Node head){
        if (head == null)
            return;
        Stack<Node> inOrderStack = new Stack<>();
        /* 原始版本
        inOrderStack.add(head);
        while (head != null || !inOrderStack.isEmpty()){
                if (head!=null && head.left != null) {
                    head = head.left;
                    inOrderStack.add(head);
                } else {
                    GraphNode peek = inOrderStack.pop();
                    System.out.print(peek.value + " ");
                    head = peek.right;
                    if (head != null)
                        inOrderStack.add(head);
                }
        }
        */

        while (head != null || !inOrderStack.isEmpty()){

            if (head!=null) {
                inOrderStack.add(head);
                head = head.left;

            } else {
                Node peek = inOrderStack.pop();
                System.out.print(peek.value + " ");
                head = peek.right;
            }



        }
    }
    public static void inOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        inOrderRecur(head.left);
        System.out.print(head.value + " ");
        inOrderRecur(head.right);
    }
    public static void postTraverse(Node head){
        //int[] lrecord = new int[3];//初始化
        //int[] rrecord = new int[3];//初始化
        Node currentPop = head;
        Node currentPeek = null;
        Stack<Node> traverseStack = new Stack<>();
        traverseStack.push(head);
        while(!traverseStack.isEmpty()){
            currentPeek = traverseStack.peek();
            if (currentPeek.left!=null && currentPeek.left!= currentPop && currentPeek.right!=currentPop){
               traverseStack.push(currentPeek.left);
            }
            else if(currentPeek.right!=null && currentPeek.right!=currentPop){
                traverseStack.push(currentPeek.right);
            }
            else {
                currentPop = traverseStack.pop();

                System.out.print(currentPop.value + " ");
            }
        }
    }
    public static void posOrderRecur(Node head) {
        if (head == null) {
            return;
        }
        posOrderRecur(head.left);
        posOrderRecur(head.right);
        System.out.print(head.value + " ");
    }
    public static void main(String[] args) {
        Node head = new Node(5);
        head.left = new Node(3);
        head.right = new Node(8);
        head.left.left = new Node(2);
        head.left.right = new Node(4);
        head.left.left.left = new Node(1);
        head.right.left = new Node(7);
        head.right.left.left = new Node(6);
        head.right.right = new Node(10);
        head.right.right.left = new Node(9);
        head.right.right.right = new Node(11);

        // recursive
        System.out.println("==============recursive==============");
        //System.out.print("pre-order: ");
        //preOrderRecur(head);
        //System.out.println();
        //System.out.print("in-order: ");
        //inOrderRecur(head);

        System.out.print("in-order: ");
        System.out.println();
        inOrderRecur(head);
        System.out.println();

        // unrecursive
        System.out.println("============unrecursive=============");
        //preOrderUnRecur(head);
        inOrder(head);
        //postTraverse(head);

    }


}
