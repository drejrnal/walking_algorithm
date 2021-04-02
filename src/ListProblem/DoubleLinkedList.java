package ListProblem;


import java.util.Iterator;

public class DoubleLinkedList<T> implements Iterable<T> {

    private static class Node<T>{
        private T key;
        private Node next;
        private Node prev;
        public Node(T _key, Node _next, Node _prev){
            this.key = _key;
            this.next = _next;
            this.prev = _prev;
        }
    }

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;
    public int size(){
        return size;
    }
    private boolean isEmpty(){
        return size() == 0;
    }
    public  void addFirst(T element){
        if (isEmpty()){
            head = tail = new Node<>(element,null,null);
        }
        else {
            head.prev = new Node(element,head,null);
            head = head.prev;
        }
        size++;
    }
    public void addLast(T element){
        if (isEmpty()){
            head = tail = new Node<>(element,null,null);
        }
        else{
            tail.next = new Node<>(element,null,tail);
            tail = tail.next;
        }
        size++;
    }
    public void addAt(int position, T element){
        if (position > size())
            throw new IndexOutOfBoundsException("Invalid inserted position");

        if(position == size()){
            addLast(element);
            return;
        }
        else {
            Node<T> trav = head;
            for (int i = 0; i < position; i++)
                trav = trav.next;
            Node<T> inserted_node = new Node<>(element, trav, trav.prev);
            if (trav.prev == null)
                trav.prev.next = inserted_node;
            else
                head = inserted_node;
            trav.prev = inserted_node;
        }
        size++;
        return;
    }
    public T removeFirst(){
        if (isEmpty())
            throw new RuntimeException("can't delete from empty list");
        if (head.next != null)
            head.next.prev = null;
        else
            tail = null;
        T data = head.key;
        head = head.next;
        size --;
        return data;
    }
    public T removeLast(){
        if (isEmpty())
            throw new RuntimeException("can't delete from empty list");
        if(tail.prev != null)
            tail.prev.next = null;
        else
            head = null;
        T data = tail.key;
        tail = tail.next;
        size --;
        return data;
    }
    public T remove(Node<T> node){
        if (node.prev == null)
            head = node.next;
        else
            node.prev.next = node.next;
        if (node.next == null)
            tail = node.prev;
        else
            node.next.prev = node.prev;
        T data = node.key;
        size --;
        node = node.next = node.prev = null;
        return data;
    }
    public T removeAt(int position){
        if (position < 0 || position >= size())
            throw new IllegalArgumentException("position exceed the size of the list");
        /*
        Node<T> trav = head;
        for (int i = 0; i< position; i++)
            trav = trav.next;
        if (trav.prev != null)
            trav.prev.next = trav.next;
        else
            head = trav.next;
        if (trav.next != null)
            trav.next.prev = trav.prev;
        else
            trav.next = null;
        T data = trav.key;
        size--;
        return data;
         */
        Node<T> trav;
        int i;
        if (position < size()/2)
            for (i=0, trav = head; i< position; i++,trav = trav.next);
        else
            for (i = size()-1,trav = tail; i>position;i--,trav = trav.prev);
        return remove(trav);
    }
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }
        };
    }
}
