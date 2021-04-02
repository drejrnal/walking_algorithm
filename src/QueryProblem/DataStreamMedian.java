package QueryProblem;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author:毛翔宇
 * @Date 2019-05-30 17:57
 */
public class DataStreamMedian {
    public class RBtree {
        private static final boolean RED = true;
        private static final boolean BLACK = false;
        private rbNode root;

        private class rbNode {
            int val;
            int N;
            rbNode left;
            rbNode right;
            boolean color;

            public rbNode(int val, int n, boolean color) {
                this.val = val;
                this.color = color;
                this.N = n;
            }
        }

        private boolean isRed(rbNode node) {
            if (node == null)
                return false;
            return node.color == RED;
        }

        private void flipColors(rbNode node) {
            node.left.color = !node.left.color;
            node.right.color = !node.right.color;
            node.color = !node.color;
        }

        private int size(rbNode node) {
            if (node == null) return 0;
            return node.N;
        }

        private int select(int k) {
            return select(root, k);
        }

        private int select(rbNode node, int k) {
            int size = size(node.left);
            if (size == k)
                return node.val;
            else if (size > k)
                return select(node.left, k);
            else
                return select(node.right, k - size - 1);
        }

        private rbNode leftRotation(rbNode x) {
            rbNode h = x.right;
            boolean color = h.color;
            h.color = x.color;
            x.color = color;
            x.right = h.left;
            h.left = x;
            h.N = x.N;
            x.N = size(x.left) + size(x.right) + 1;
            return h;
        }

        private rbNode rightRotation(rbNode x) {
            rbNode h = x.left;
            boolean color = h.color;
            h.color = x.color;
            x.color = color;
            x.left = h.right;
            h.right = x;
            h.N = x.N;
            x.N = size(x.left) + size(x.right) + 1;
            return h;
        }

        public void put(int val) {
            root = put(root, val);
            root.color = BLACK;
        }

        public rbNode put(rbNode node, int val) {
            if (node == null)
                return new rbNode(val, 1, RED);
            int value = node.val;
            if (val <= value)
                node.left = put(node.left, val);
            else
                node.right = put(node.right, val);
            if (isRed(node.right) && !isRed(node.left))
                node = leftRotation(node);
            if (isRed(node.left) && isRed(node.left.left))
                node = rightRotation(node);
            if (isRed(node.left) && isRed(node.right))
                flipColors(node);
            node.N = size(node.left) + size(node.right) + 1;
            return node;
        }

        public void printByLevelOrder(rbNode node) {
            Queue<rbNode> queue = new LinkedList<>();
            queue.add(node);

            while (!queue.isEmpty()) {
                rbNode first = queue.poll();
                System.out.print(first.val + " ");

                if (first.left != null) {
                    queue.offer(first.left);

                }
                if (first.right != null) {
                    queue.offer(first.right);

                }

            }
        }

        private boolean isBalanced() {
            int black = 0;     // number of black links on path from root to min
            rbNode x = root;
            while (x != null) {
                if (!isRed(x)) black++;
                x = x.left;
            }
            return isBalanced(root, black);
        }

        // does every path from the root to a leaf have the given number of black links?
        private boolean isBalanced(rbNode x, int black) {
            if (x == null) return black == 0;
            if (!isRed(x)) black--;
            return isBalanced(x.left, black) && isBalanced(x.right, black);
        }
    }

    private int count;
    private RBtree rBtree;

    public DataStreamMedian() {
        rBtree = new RBtree();
        count = 0;
    }

    public void addNumber(int number) {
        rBtree.put(number);
        count += 1;
    }

    public double select(int rank) {
        return rBtree.select(rank);
    }

    public double findMedian() {
        return (select(count / 2) + select((count - 1) / 2)) / 2;
    }

    public boolean isBalance() {
        return rBtree.isBalanced();
    }

    public void print() {
        rBtree.printByLevelOrder(rBtree.root);
    }

    public static void main(String[] args) {
        DataStreamMedian dsm = new DataStreamMedian();
        for (int i = 0; i < 20; i++)
            dsm.addNumber(i);
        dsm.print();
        System.out.println(dsm.findMedian());
    }
}
