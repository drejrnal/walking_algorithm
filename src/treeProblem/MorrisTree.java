package treeProblem;

public class MorrisTree {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void morrisTraversePre(Node head) {
        System.out.println("=====PreOrderTraverse====");
        Node current = head;
        while (current != null) {
            Node mostRight = current.left;
            //第一大分支：当前节点左子树不为空
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != current) {
                    mostRight = mostRight.right;
                }
                //第一次到达非叶子节点
                if (mostRight.right == null) {
                    System.out.print(current.value + " ");
                    mostRight.right = current;
                    current = current.left;
                }
                //第二次到达非叶子结点
                else {
                    mostRight.right = null;
                    current = current.right;
                }
            } else {
                System.out.print(current.value + " ");
                current = current.right;
            }
        }
        System.out.println();
    }

    public static void morrisTraverseIn(Node head) {
        System.out.println("=====InOrderTraverse====");
        Node current = head;
        while (current != null) {
            Node mostRight = current.left;
            //第一大分支：当前节点左子树不为空
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != current) {
                    mostRight = mostRight.right;
                }
                //第一次到达非叶子节点
                if (mostRight.right == null) {
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }
                //第二次到达非叶子结点
                else {
                    mostRight.right = null;
                }
            }
            System.out.print(current.value + " ");
            current = current.right;
        }
        System.out.println();
    }

    public static void morrisTraversePro(Node head) {
        System.out.println("=====ProOrderTraverse====");
        Node current = head;
        while (current != null) {
            Node mostRight = current.left;
            //第一大分支：当前节点左子树不为空
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != current) {
                    mostRight = mostRight.right;
                }
                //第一次到达非叶子节点
                if (mostRight.right == null) {
                    mostRight.right = current;
                    current = current.left;
                }
                //第二次到达非叶子结点
                else {

                    mostRight.right = null;
                    printEdge(current.left);
                    current = current.right;
                }
            } else {
                current = current.right;
            }
        }
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(Node traverse) {
        Node tail = reverse(traverse);
        Node tmp = tail;
        while (tmp != null) {
            System.out.print(tmp.value + " ");
            tmp = tmp.right;
        }
        reverse(tail);
    }

    public static Node reverse(Node begin) {
        Node next = null;
        while (begin != null) {
            Node tmp = begin.right;
            begin.right = next;
            next = begin;
            begin = tmp;
        }
        return next;
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
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisTraverseIn(head);
        morrisTraversePre(head);
        morrisTraversePro(head);
        //printEdge(head.left);
        //System.out.println(head.left.right.value);
        printTree(head);
    }
}
