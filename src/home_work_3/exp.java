package home_work_3;

public class exp {

    static class Node {
        Node left;
        Node right;
        Node parent;
        int value;

        public Node(int value) {
            this.value = value;
        }
    }
    public  static void swapBetweenNodes(Node swapNode, Node swapedNode){
        System.out.println("函数参数地址"+String.valueOf(swapNode)+";"+String.valueOf(swapedNode));

        Node swapedNodeParent = swapedNode.parent;

        Node swapedNodeLeft = swapedNode.left;
        Node swapedNodeRight = swapedNode.right;
        Node swapNodeLeft = swapNode.left;
        Node swapNodeRight = swapNode.right;

        swapNode.parent = swapedNodeParent;
        if (swapedNodeParent != null){
            if (swapedNode == swapedNodeParent.left){
                swapedNodeParent.left = swapNode;
            }
            else
                swapedNodeParent.right = swapNode;
        }
        swapedNode.parent = swapNode;
        if (swapNode == swapedNodeLeft){
            swapNode.left = swapedNode;
            swapNode.right = swapedNodeRight;
            if (swapedNodeRight != null)
                swapedNodeRight.parent = swapNode;
        }
        else/* if(swapNode == swapedNodeRight)*/{
            swapNode.left = swapedNodeLeft;
            swapNode.right = swapedNode;
            swapedNodeLeft.parent = swapNode;
        }
        if (swapNodeLeft != null)
            swapNodeLeft.parent = swapedNode;
        if (swapNodeRight!= null)
            swapNodeRight.parent = swapedNode;
        swapedNode.left = swapNodeLeft;
        swapedNode.right = swapNodeRight;
        System.out.println("右侧参数节点的值"+String.valueOf(swapNode.right.value));
        swapNode = swapNode.parent;
        System.out.println("============"+swapNode.value);
        System.out.println("左侧参数节点的值"+String.valueOf(swapedNode.value));
    }

    public static Node mostLeftSubtree(Node sink) {
        //保证sink节点非空
        while (sink.left != null)
            sink = sink.left;
        System.out.println(sink);
        return sink;
    }
    public static void main(String[] argv){
        Node parent = new Node(3);
        Node left = new Node(1);
        Node right = new Node(2);
        left.parent = right.parent = parent;
        Node leftleft = new Node(4);
        Node leftright = new Node(5);
        leftleft.parent = leftright.parent = left;
        Node rightleft = new Node(6);
        rightleft.parent = right;
        left.left = leftleft;
        left.right = leftright;
        right.left = rightleft;
        parent.left = left;
        parent.right = right;

        //说明函数实参数中的parent不随函数形参节点遍历而更改引用位置，只是改变引用对象本身的值
        System.out.println("最左子树节点"+mostLeftSubtree(parent).value + "；"+parent );











        System.out.println("主函数参数地址："+String.valueOf(leftleft)+";"+String.valueOf(left));
        swapBetweenNodes(leftleft,left);
        System.out.println("主函数参数地址："+String.valueOf(leftleft)+";"+String.valueOf(left));
        System.out.printf("%d,%d",left.value,leftleft.right.value);
        System.out.println();
    }
}
