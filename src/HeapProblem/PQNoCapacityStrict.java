package HeapProblem;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author:毛翔宇
 * @Date 2019/1/22 ~ 2019/1/23 11:04 AM
 * 完全二叉树的构建（添加节点、删除节点【keep track of the change of last node】）
 */
public class PQNoCapacityStrict<T extends Comparable<T>> {
    /*
容量无上限的堆结构
取元素、添加元素时间复杂度均为O(1)
 */

    class Node<T> {
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

    //greaterThan
    public static class smallRoot implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }

    //smallerThan
    public static class bigRoot implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

    /*
    class field
     */
    private Node<T> head;
    private Node<T> last;
    private Comparator<T> comparator;
    private int size;

    public PQNoCapacityStrict(Comparator<T> myComparator) {
        head = null;
        last = null;
        size = 0;
        comparator = myComparator;
    }

    /*
    class Method
     */
    public Node<T> mostLeftSubtree(Node<T> sink) {
        //保证sink节点非空
        while (sink.left != null)
            sink = sink.left;
        return sink;
    }

    public Node<T> mostRightSubtree(Node<T> sink) {
        while (sink.right != null)
            sink = sink.right;
        return sink;
    }

    public void add(T value) {
        Node<T> newNode = new Node<T>(value);
        if (size == 0) {
            head = last = newNode;
            size++;
        } else {
            /*
                情况1：last节点为完全二叉树最深层的最后一节点
                情况2：last节点为左孩子
                情况3：last节点为右孩子
            */
            Node<T> tail = last;
            Node<T> tmpParent = tail.parent;
            //寻找tail节点的后续节点
            while (tmpParent != null && tail != tmpParent.left) {
                tail = tmpParent;
                tmpParent = tmpParent.parent;
            }
            if (tmpParent == null) {
                Node<T> mostLeft = mostLeftSubtree(head);
                mostLeft.left = newNode;
                newNode.parent = mostLeft;
            } else if (tmpParent.right == null) {
                tmpParent.right = newNode;
                newNode.parent = tmpParent;
            } else {
                Node<T> mostLeft = mostLeftSubtree(tmpParent.right);
                mostLeft.left = newNode;
                newNode.parent = mostLeft;
            }
            last = newNode;
            size++;
        }
        swim(last);

    }

    public T popHead() {
        T result = head.value;
        if (size == 1) {
            head = last = null;
            size--;
            return result;
        }
        Node<T> oldLast = popLastAndSetLast();
        //System.out.println(oldLast.value+" "+last.value);
        if (size == 1) {
            head = last = oldLast;
            return result;
        }
        Node<T> contextLeft = head.left;
        Node<T> contextRight = head.right;
        head.left = head.right = null;
        oldLast.left = contextLeft;
        if (contextLeft != null)
            contextLeft.parent = oldLast;
        oldLast.right = contextRight;
        if (contextRight != null)
            contextRight.parent = oldLast;
        head = oldLast;
        sink(oldLast);
        return result;
    }

    public Node<T> popLastAndSetLast() {
        Node<T> oldLast = last;
        Node<T> tmpParent = last.parent;
        while (tmpParent != null && last != tmpParent.right) {
            last = tmpParent;
            tmpParent = tmpParent.parent;
        }
        if (tmpParent == null) {
            //删除oldlast上下文环境，因为要将oldlast换到head位置
            Node<T> oldParent = oldLast.parent;
            if (oldLast == oldParent.left)
                oldParent.left = null;
            else
                oldParent.right = null;
            oldLast.parent = null;
            last = mostRightSubtree(last);
        } else {
            last = mostRightSubtree(tmpParent.left);
            Node<T> parent = oldLast.parent;
            oldLast.parent = null;
            if (oldLast == parent.left)
                parent.left = null;
            else
                parent.right = null;
        }
        size--;
        return oldLast;
    }

    public void swapParentChildNodes(Node<T> parent, Node<T> child) {
        //parents上下文环境
        Node<T> parentLeft = parent.left;
        Node<T> parentRight = parent.right;
        //child上下文环境
        Node<T> childLeft = child.left;
        Node<T> childRight = child.right;
        //改变节点父亲环境
        child.parent = parent.parent;
        if (parent.parent == null)
            head = child;
        else {
            if (parent == parent.parent.left)
                child.parent.left = child;
            else
                child.parent.right = child;
        }
        parent.parent = child;
        //改变节点孩子环境
        if (parentLeft == child) {
            child.left = parent;
            child.right = parentRight;
            if (parentRight != null)
                parentRight.parent = child;
        } else if (parentRight == child) {
            child.left = parentLeft;
            child.right = parent;
            if (parentLeft != null)
                parentLeft.parent = child;
        } else {
            child.left = parentLeft;
            if (parentLeft != null)
                parentLeft.parent = child;
            child.right = parentRight;
            if (parentRight != null)
                parentRight.parent = child;
        }
        parent.left = childLeft;
        if (childLeft != null)
            childLeft.parent = parent;
        parent.right = childRight;
        if (childRight != null)
            childRight.parent = parent;
    }

    //堆调整
    public void swim(Node<T> tail) {
        Node<T> tmpParent = tail.parent;
        //由于交换节点可能更新last指针，因此提前更新last
        if (tmpParent != null && comparator.compare(tmpParent.value, tail.value) > 0) {
            last = tmpParent;
        }
        while (tmpParent != null && comparator.compare(tmpParent.value, tail.value) > 0) {
            swapParentChildNodes(tmpParent, tail);
            tmpParent = tail.parent;
        }
        //交换节点可能会更改head指针，因此需要更新
        if (head.parent != null)
            head = head.parent;
    }

    public void sink(Node<T> walk) {
        while (walk.left != null) {
            Node<T> walkDown = walk.left;
            if (walk.right != null && comparator.compare(walkDown.value, walk.right.value) > 0) {
                walkDown = walk.right;
            }
            if (!(comparator.compare(walk.value, walkDown.value) > 0))
                break;
            else {
                //System.out.println(walk.value+" "+walkDown.value);
                swapParentChildNodes(walk, walkDown);
            }
        }
        /*
        下沉过程中会改变head和last，因此需要重新设置
         */
        if (walk.parent == last)
            last = walk;
        while (walk.parent != null)
            walk = walk.parent;
        head = walk;
    }

    // for test
    public void printHeapByLevel() {
        if (this.size == 0) {
            System.out.println("Heap Empty!");
            return;
        }
        System.out.println("Head: " + this.head.value);
        System.out.println("Last: " + this.last.value);
        System.out.println("Size: " + this.size);
        printBinaryTreeByDepth(this.head);
    }

    // for test
    private void printBinaryTreeByDepth(Node<T> head) {
        Queue<Node<T>> nodeQueue = new LinkedList<Node<T>>();
        Node<T> levelLastNode = head;
        Node<T> nextLevelLastNode = null;
        int levelNum = 0;
        nodeQueue.add(head);
        System.out.print("Level 0 nodes: ");
        while (!nodeQueue.isEmpty()) {
            Node<T> current = nodeQueue.poll();
            System.out.print(current.value + " ");
            if (current.left != null) {
                nextLevelLastNode = current.left;
                nodeQueue.add(current.left);
            }
            if (current.right != null) {
                nextLevelLastNode = current.right;
                nodeQueue.add(current.right);
            }
            if (current == levelLastNode) {
                levelLastNode = nextLevelLastNode;
                nextLevelLastNode = null;
                System.out.println();
                if (levelLastNode != null) {
                    System.out.print("Level " + (++levelNum) + " nodes: ");
                }
            }
        }
    }

    // for test
    public void changeHead(T value) {
        if (this.head != null) {
            Node<T> newNode = new Node<T>(value);
            Node<T> headLeft = this.head.left;
            Node<T> headRight = this.head.right;
            if (headLeft != null) {
                headLeft.parent = newNode;
                newNode.left = headLeft;
            }
            if (headRight != null) {
                headRight.parent = newNode;
                newNode.right = headRight;
            }
            this.head.left = null;
            this.head.right = null;
            this.head = newNode;
            this.sink(this.head);
        }
    }

    public static void main(String[] args) {
        PQNoCapacityStrict<Integer> test = new PQNoCapacityStrict<>(new bigRoot());
        test.add(4);
        test.add(6);
        test.add(8);
        test.changeHead(3);
        test.add(9);
        test.add(10);
        test.add(1);
        test.printHeapByLevel();
        System.out.println(test.popHead());
        test.printHeapByLevel();
        test.add(7);
        test.printHeapByLevel();
        test.add(10);
        test.add(11);
        test.printHeapByLevel();

        System.out.println(test.popHead());
        test.printHeapByLevel();
        System.out.println(test.popHead());
        test.printHeapByLevel();
        System.out.println(test.popHead());
        System.out.println(test.popHead());
        System.out.println(test.popHead());
        System.out.println(test.popHead());
        test.printHeapByLevel();
    }
}
