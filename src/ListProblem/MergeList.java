package ListProblem;

/**
 * @Author:毛翔宇
 * @Date 2019-03-14 14:32
 */
public class MergeList {

    public static class ListNode{
        int value;
        ListNode next;
        ListNode(int val){
            this.value = val;
        }
    }
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2){
        ListNode prev = new ListNode(0);
        ListNode res = prev;
        while(l1 != null && l2 != null){
            if (l1.value > l2.value){
                prev.next = l2;
                l2 = l2.next;
                prev = prev.next;
            }
            else{
                prev.next = l1;
                l1 = l1.next;
                prev = prev.next;
            }
        }
        /*while (l1 != null){
            prev.next = l1;
            l1 = l1.next;
            prev = prev.next;
        }
        while(l2 != null){
            prev.next = l2;
            l2 = l2.next;
            prev = prev.next;
        }*/
        prev.next = l1 != null?l1:l2;
        return res.next;
    }
    public static class IndexMinPQ<Key extends Comparable<Key>>{
        Key[] keys;
        int[] pq;
        int[] qp;
        int size;
        //heap begin based on index-1
        public IndexMinPQ(int capacity){
            keys = (Key[]) new Comparable[capacity+1];
            pq = new int[capacity+1];
            qp = new int[capacity+1];
            size = 0;
            for (int i = 1;i<qp.length;i++)
                qp[i] = -1;
        }
        public int minIndex(){
            return pq[1];
        }
        public void insert(int position,Key key){
            keys[position] = key;
            size++;
            pq[size] = position;
            qp[position] = size;
            swim(size);
        }
        public void change(int position,Key key){
            keys[position] = key;
            //if change key much bigger
            sink(qp[position]);
            //if change key much smaller
            swim(qp[position]);
        }
        public void delete(int position){
            int index = qp[position];
            swap(index,size--);
            swim(index);
            sink(index);
            keys[position] = null;
            qp[position] = -1;
        }
        /*
        dfs method
         */
        public void swim(int pos){
            while(pos/2 != 0){
                if (greater(pos/2,pos)){
                    swap(pos/2,pos);
                }
                else
                    break;
                pos = pos/2;
            }
        }
        public void sink(int pos){
            while (2 * pos <= size){
                int i = 2 *pos;
                if (i+1<=size && greater(i,i+1)) i= i+1;
                if (greater(pos,i)){
                    swap(pos,i);
                }
                else break;
                pos = i;
            }
        }
        public boolean greater(int pos1,int pos2){
            return keys[pq[pos1]].compareTo(keys[pq[pos2]])>0;
        }
        public void swap(int pos1,int pos2){
            int tmp = pq[pos1];
            pq[pos1] = pq[pos2];
            pq[pos2] = tmp;
            qp[pq[pos1]] = pos1;
            qp[pq[pos2]] = pos2;
        }
    }
    public static ListNode mergeKLists(ListNode[] lists){
        IndexMinPQ<Integer> indexPQ = new IndexMinPQ<Integer>(lists.length);
        for (int i = 0;i<lists.length ;i++) {
            if (lists[i] != null) {
                indexPQ.insert(i+1, lists[i].value);
            }
        }
        ListNode head = new ListNode(0);
        ListNode res = head;
        while (indexPQ.size >1){
            int index = indexPQ.minIndex()-1;
            res.next = lists[index];
            res = res.next;
            lists[index] = lists[index].next;
            if (lists[index]!=null)
                indexPQ.change(index+1,lists[index].value);
            else
                indexPQ.delete(index+1);
        }
        int index = indexPQ.minIndex()-1;
        System.out.println("index:"+index );
        ListNode finale = null;
        if (index >= 0)
            finale = lists[index];
        res.next = finale;
        return head.next;
    }

    //divide and conquer
    public static ListNode mergeKListsTwo(ListNode[] lists){
        if (lists == null)
            return null;
        int start = 0, end = lists.length-1;
        return process(lists,start,end);
    }
    public static ListNode process(ListNode[] lists,int begin,int end){
        if (begin == end)
            return lists[begin];
        int mid = (begin+end)/2;
        ListNode left = process(lists,begin,mid);
        ListNode right = process(lists,mid+1,end);
        return mergeTwoLists(left,right);
    }

    public static void main(String[] args){
        ListNode head1 = new ListNode(1);
        head1.next  = new ListNode(3);
        head1.next.next = new ListNode(4);

        ListNode head2 = new ListNode(1);
        head2.next  = new ListNode(4);
        head2.next.next = new ListNode(5);

        ListNode head3 = new ListNode(2);
        head3.next  = new ListNode(6);

        ListNode[] listNodes = new ListNode[3];
        listNodes[0] = head1;
        listNodes[1] = null;
        listNodes[2] = head3;
        ListNode res = mergeKListsTwo(listNodes);
        while ( res != null){
            System.out.println(res.value);
            res = res.next;
        }
    }
}
