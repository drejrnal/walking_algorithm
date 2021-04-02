package designProblem;

import java.util.HashMap;
import java.util.Stack;

public class MaxTree {


    class Node {
        int value;
        Node parent;
        Node left;
        Node right;
        int index;

        public Node(int data) {
            this.value = data;
        }
    }

    Node root;
    HashMap<Node, Node> leftNearestMax = new HashMap<>();
    HashMap<Node, Node> rightNearestMax = new HashMap<>();

    public void geneMap(Node[] nodeArray) {
        if (nodeArray.length == 0)
            return;
        Stack<Node> monotonousStack = new Stack<>();
        monotonousStack.push(nodeArray[0]);
        for (int i = 1; i < nodeArray.length; i++) {
            while (!monotonousStack.isEmpty() && nodeArray[i].value > monotonousStack.peek().value) {
                Node tmp = monotonousStack.pop();
                rightNearestMax.put(tmp, nodeArray[i]);
                if (!monotonousStack.isEmpty())
                    leftNearestMax.put(tmp, monotonousStack.peek());
                else
                    leftNearestMax.put(tmp, null);

            }
            monotonousStack.push(nodeArray[i]);
        }
        while (!monotonousStack.isEmpty()) {
            Node tmp = monotonousStack.pop();
            if (!monotonousStack.isEmpty())
                leftNearestMax.put(tmp, monotonousStack.peek());
            else
                leftNearestMax.put(tmp, null);

        }
    }

    public int[] geneTree(int[] seriel) {
        Node[] nodeArr = new Node[seriel.length];
        for (int i = 0; i < seriel.length; i++) {
            nodeArr[i] = new Node(seriel[i]);
            nodeArr[i].index = i;
        }
        geneMap(nodeArr);
        for (int i = 0; i < nodeArr.length; i++) {
            Node lNmax = leftNearestMax.get(nodeArr[i]);
            Node rNmax = rightNearestMax.get(nodeArr[i]);
            if (lNmax == null && rNmax == null) {
                root = nodeArr[i];
            } else if (lNmax == null) {
                nodeArr[i].parent = rNmax;
                if (rNmax.left == null)
                    rNmax.left = nodeArr[i];
                else
                    rNmax.right = nodeArr[i];
            } else if (rNmax == null) {
                nodeArr[i].parent = lNmax;
                if (lNmax.left == null)
                    lNmax.left = nodeArr[i];
                else
                    lNmax.right = nodeArr[i];
            } else {
                Node cparent = lNmax.value < rNmax.value ? lNmax : rNmax;
                nodeArr[i].parent = cparent;
                if (cparent.left == null)
                    cparent.left = nodeArr[i];
                else
                    cparent.right = nodeArr[i];
            }

        }
        int[] res = new int[seriel.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = (nodeArr[i] == root) ? -1 : nodeArr[i].parent.index;
        }

        return res;
    }

    public static void printPreOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        printPreOrder(head.left);
        printPreOrder(head.right);
    }

    public static void printInOrder(Node head) {
        if (head == null) {
            return;
        }
        printInOrder(head.left);
        System.out.print(head.value + " ");
        printInOrder(head.right);
    }

    public static void main(String[] args) {
        int[] uniqueArr = {3, 4, 5, 1, 2};
        MaxTree mTree = new MaxTree();

        //GraphNode head = (mTree.geneTree(uniqueArr));
        int[] re = mTree.geneTree(uniqueArr);
        for (int i = 0; i < re.length; i++)
            System.out.println(re[i]);
        //printPreOrder(head);
        //System.out.println();
        //printInOrder(head);

    }

}
