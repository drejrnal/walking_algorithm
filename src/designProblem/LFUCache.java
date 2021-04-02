package designProblem;

import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2018/9/1 下午9:10
 */


public class LFUCache {
    class Node {
        int key;
        int value;
        int frequency;
        Node down = null;
        Node up = null;

        Node(int key,int value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    class BucketList {
        Node head ;
        Node tail ;
        BucketList next;
        BucketList previous;

        public BucketList(Node newNode) {
            head = newNode;
            tail = newNode;
        }

        public boolean isEmpty(){
            return head == null;
        }
        public void addNodeFromHead(Node newNode) {
            newNode.down = head;
            head.up = newNode;
            head = newNode;
        }

        public void deleteNode(Node oldNode) {
            //列表中只有1个元素
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (oldNode == head) {
                    head = oldNode.down;
                    head.up = null;
                } else if (oldNode == tail) {
                    tail = oldNode.up;
                    tail.down = null;
                } else {
                    oldNode.up.down = oldNode.down;
                    oldNode.down.up = oldNode.up;
                }
                oldNode.up = null;
                oldNode.down = null;
            }
        }
    }
    int capacity;
    int size ;
    BucketList bucketHead;
    HashMap<Integer, Node> keyToNode;
    HashMap<Node, BucketList> nodeToBucket;

    public LFUCache(int capacity){
        this.capacity = capacity;
        this.size = 0;
        keyToNode = new HashMap<>();
        nodeToBucket = new HashMap<>();
        bucketHead = null;
    }
    public void put(int key, int value) {
        if (keyToNode.containsKey(key)) {
            Node tmpNode = keyToNode.get(key);
            tmpNode.value = value;
            BucketList currBucket = nodeToBucket.get(tmpNode);
            tmpNode.frequency += 1;
            move(tmpNode, currBucket);
        } else {

            if (capacity == 0)
                return;
            if (size == capacity) {
                Node tmp = bucketHead.tail;
                bucketHead.deleteNode(tmp);
                deleteNodeAndModifyHead(bucketHead);
                keyToNode.remove(tmp.key);
                nodeToBucket.remove(tmp);
                size--;
            }
            Node newNode = new Node(key,value, 1);
            keyToNode.put(key, newNode);
            if (bucketHead == null) {
                bucketHead = new BucketList(newNode);
            } else {

                if (bucketHead.head.frequency == 1)
                    bucketHead.addNodeFromHead(newNode);
                else {
                    BucketList newBucket = new BucketList(newNode);
                    newBucket.next = bucketHead;
                    bucketHead.previous = newBucket;
                    bucketHead = newBucket;
                }
            }
            size++;
            nodeToBucket.put(newNode, bucketHead);
        }

    }

    void move(Node tmpNode, BucketList currentBucket) {
        currentBucket.deleteNode(tmpNode);
        BucketList bucketNext = currentBucket.next;
        BucketList preBucket = deleteNodeAndModifyHead(currentBucket) ? currentBucket.previous : currentBucket;
        if (bucketNext == null) {
            BucketList newBucket = new BucketList(tmpNode);
            newBucket.next = null;
            newBucket.previous = preBucket;
            if (preBucket == null)
                bucketHead = newBucket;
            else
                preBucket.next = newBucket;
            nodeToBucket.put(tmpNode, newBucket);
        } else {
            if (bucketNext.head.frequency == tmpNode.frequency) {
                bucketNext.previous = preBucket;
                if (preBucket != null)
                    preBucket.next = bucketNext;
                bucketNext.addNodeFromHead(tmpNode);
                nodeToBucket.put(tmpNode, bucketNext);
            } else {
                BucketList newBucket = new BucketList(tmpNode);
                if (preBucket != null)
                    preBucket.next = newBucket;
                newBucket.next = bucketNext;
                bucketNext.previous = newBucket;
                newBucket.previous = preBucket;
                if (bucketHead == bucketNext)
                    bucketHead = newBucket;
                nodeToBucket.put(tmpNode, newBucket);
            }
        }
    }

    boolean deleteNodeAndModifyHead(BucketList currentBucket) {
        if (currentBucket.isEmpty()) {
            if (currentBucket == bucketHead) {
                bucketHead = currentBucket.next;
                if (bucketHead != null)
                    bucketHead.previous = null;
            } else {
                currentBucket.previous.next = currentBucket.next;
                if (currentBucket.next != null)
                    currentBucket.next.previous = currentBucket.previous;

            }
            return true;
        }
        return false;
    }

    public int get(int key) {
        if (!keyToNode.containsKey(key))
            return -1;
        Node tmp = keyToNode.get(key);
        BucketList curr = nodeToBucket.get(tmp);
        int re = tmp.value;
        tmp.frequency += 1;
        move(tmp, curr);
        return re;
    }


    public static void main(String args[]){
        LFUCache cache = new LFUCache( 2 /* capacity */ );
        cache.put(3,1);
        cache.put(2,1);
        cache.put(2,2);
        cache.put(4,4);
        System.out.println(cache.get(2));
        /*cache.put(1, 1);
        cache.put(2, 2);

        System.out.println(cache.get(1));       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println(cache.get(2));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3.
        cache.put(4, 4);    // evicts key 1.
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4*/
    }
}
