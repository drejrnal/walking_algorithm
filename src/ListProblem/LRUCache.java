package ListProblem;



import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2018/10/14 下午10:27
 */

public class LRUCache {
    class Node{
        int key;
        int value;
        Node previous;
        Node next;
        public Node(int key,int value){
            this.key = key;
            this.value = value;
            previous = null;
            next = null;
        }
    }
    public LRUCache(int capacity){
        this.head = null;
        this.tail = null;
        this.capacity = capacity;
        this.keyToNode = new HashMap<>();
        this.nodeToKey = new HashMap<>();
    }
    private int capacity;
    private Node head ;
    private Node tail;
    private HashMap<Integer, Node> keyToNode ;
    private HashMap<Node, Integer> nodeToKey;

    public void put(int key, int value){
        if (!this.keyToNode.containsKey(key)){
            Node breedNode = new Node(key,value);
            this.keyToNode.put(key,breedNode);
            this.nodeToKey.put(breedNode,key);
        }
        else{
            Node existNode = this.keyToNode.get(key);
            existNode.value = value;
        }
        if (this.capacity < this.keyToNode.size()){
            deleteNodeFromHead();
        }
        ModifyNodeList(key);
    }
    public int get(int key){
        if (!keyToNode.containsKey(key))
            return -1;
        else {
            ModifyNodeList(key);
            return keyToNode.get(key).value;
        }
    }
    private void deleteNodeFromHead(){
        if (this.head == null){
            return;
        }
        int deleteKey = nodeToKey.get(this.head);
        if (this.head.next != null) {
            this.head.next.previous = null;
        }
        if (this.head == this.tail)
            this.tail = null;
        Node tmp = this.head;
        this.head = this.head.next;
        keyToNode.remove(deleteKey);
        nodeToKey.remove(tmp);
    }
    private void ModifyNodeList(int key){
        Node tmpNode = keyToNode.get(key);
        if (this.tail == tmpNode)
            return;
        if (this.head == null) {
            this.head = tmpNode;
        }
        else {
            if (this.head == tmpNode && this.head.next != null) {
                this.head = tmpNode.next;
            }
            if (tmpNode.next != null)
                tmpNode.next.previous = tmpNode.previous;
            if (tmpNode.previous != null)
                tmpNode.previous.next = tmpNode.next;
        }
        if ( this.tail!= null ) {
            this.tail.next = tmpNode;
            tmpNode.previous = this.tail;
            tmpNode.next = null;
        }
        this.tail = tmpNode;
    }
    public static void main(String args[]){

        LRUCache cache = new LRUCache( 2 /* capacity */ );

        cache.put(2, 1);
        cache.put(2, 2);
        System.out.println(cache.get(2));
        cache.put(1, 1);    // evicts key 2
        //cache.get(2);      // returns -1 (not found)
        cache.put(4, 1);    // evicts key 1
        cache.get(2);       // returns -1 (not found)


        for (Node i = cache.head;i!= null;i = i.next)
            System.out.println(i.key);

    }
}
