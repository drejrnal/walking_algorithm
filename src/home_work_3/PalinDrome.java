package home_work_3;

import java.util.Stack;

public class PalinDrome {
    private static class listNode {
        int val;
        listNode next = null;

        listNode(int val) {
            this.val = val;
        }
    }

    // need n extra space
    public static boolean isPalindrome1(listNode head) {
        Stack<listNode> stack = new Stack<listNode>();
        listNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    // need n/2 extra space
    public static boolean isPalindrome2(listNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        listNode right = head.next;
        listNode cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        Stack<listNode> stack = new Stack<listNode>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }
    public static boolean isPalindrome(listNode head) {
        if (head == null || head.next == null)
            return true;
        boolean re = true;
        listNode slowpointer = head;
        listNode fastpointer = head;
        while (fastpointer.next != null && fastpointer.next.next != null) {
            slowpointer = slowpointer.next;
            fastpointer = fastpointer.next.next;
        }
        listNode right_traverse = slowpointer.next;
        listNode tmp;
        fastpointer = null;
        while (right_traverse!=null){
            tmp = right_traverse.next;
            right_traverse.next = fastpointer;
            fastpointer = right_traverse;
            right_traverse = tmp;
        }
        slowpointer = head;
        right_traverse = fastpointer;
        while (fastpointer != null && slowpointer != null) {
            if (fastpointer.val != slowpointer.val) {
                re = false;
                break;
            }
            fastpointer = fastpointer.next;
            slowpointer = slowpointer.next;
        }
        fastpointer = null;
        while (right_traverse!=null){
            tmp = right_traverse.next;
            right_traverse.next = fastpointer;
            fastpointer = right_traverse;
            right_traverse = tmp;
        }
        return re;
    }
    public static void printLinkedList(listNode node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        listNode head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(3);
        head.next.next.next = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(2);
        head.next.next.next = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new listNode(1);
        head.next = new listNode(2);
        head.next.next = new listNode(3);
        head.next.next.next = new listNode(2);
        head.next.next.next.next = new listNode(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}
