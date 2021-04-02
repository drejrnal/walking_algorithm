package ListProblem;

public class ListPartition {
    public static class ListNode{
        int value;
        ListNode next;

        public ListNode(int value){
            this.value = value;
        }
    }

    public static ListNode listPartition(ListNode head,int pivot){
        ListNode bigHead = null;
        ListNode bigTail = null;
        ListNode equalHead = null;
        ListNode equalTail = null;
        ListNode smallHead = null;
        ListNode smallTail = null;
        while(head != null){
            ListNode tmp = head.next;
            head.next = null;
            if (head.value > pivot){
                if (bigHead == null){
                    bigHead = head;
                    bigTail = head;
                }
                else {
                    bigTail.next = head;
                    bigTail = head;
                }
            }
            else if(head.value == pivot){
                if (equalHead == null){
                    equalHead = head;
                    equalTail = head;
                }
                else {
                    equalTail.next = head;
                    equalTail = head;
                }
            }
            else{
                if (smallHead == null){
                    smallHead = head;
                    smallTail = head;
                }
                else {
                    smallTail.next = head;
                    smallTail = head;
                }
            }
            head = tmp;
        }
        if (smallTail != null){
            smallTail.next = equalHead;
            equalTail = equalHead == null? smallTail:equalTail;
        }
        if (equalTail != null){
            equalTail.next = bigHead;
            head = smallTail == null?equalHead:smallHead;
        }
        else
            head = bigHead;
        return head;

    }

    public static void printLinkedList(ListNode node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(7);
        head1.next = new ListNode(9);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(8);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(10);
        head1.next.next.next.next.next.next = new ListNode(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition(head1, 4);
        printLinkedList(head1);

    }



}
