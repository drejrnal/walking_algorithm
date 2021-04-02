package HeapProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:毛翔宇
 * @Date 2019-03-06 15:11
 */
public class FreqTopK {
    public class Node{
        private String word;
        private int freq;
        public Node(String _word,int times){
            this.word = _word;
            this.freq = times;
        }
    }
    private StaticDataHeap staticDataHeap;
    private DynamicDataHeap dynamicDataHeap;
    public FreqTopK(String[] words,int topK){
        staticDataHeap = new StaticDataHeap(words,topK);
        dynamicDataHeap = new DynamicDataHeap(topK);
    }
    public class StaticDataHeap{
        private HashMap<String,Integer> wordToFreq;
        private Node[] heap;
        public StaticDataHeap(String[] words,int topK){
            wordToFreq = new HashMap<>();
            heap = new Node[topK];
            for (String word:words){
                if (wordToFreq.containsKey(word))
                    wordToFreq.put(word,wordToFreq.get(word)+1);
                else
                    wordToFreq.put(word,1);
            }
        }
        public Node[] getTopK(){
            int index = 0;
            for (Map.Entry<String,Integer> entry:wordToFreq.entrySet()){
                String key = entry.getKey();
                int freq = entry.getValue();
                Node node = new Node(key,freq);
                if (index != heap.length){
                    heap[index] = node;
                    heapInsert(index++);
                }
                else{
                    if (greater(node,heap[0])>0){
                        heap[0] = node;
                        heapify(index);
                    }
                }
            }
            for(int i = index -1;i>0;i--){
                swap(0,i);
                heapify(i);
            }
            return heap;
        }
        public void heapInsert(int size){
            while( size > 0){
                int parent = (size-1)/2;
                if (greater(heap[parent],heap[size])>0){
                    swap(parent,size);
                }
                else
                    break;
                size = parent;
            }
        }
        public void heapify(int size){
            int i = 0;
            while ( (2 * i +1) < size ){
                int child1 = 2*i+1;
                if (child1+1 < size && greater(heap[child1],heap[child1+1])>0)
                    child1 = child1+1;
                if (greater(heap[i],heap[child1])>0){
                    swap(i,child1);
                    i = child1;
                }
                else
                    break;
            }
        }
        public int greater(Node node1,Node node2){
            if (node1.freq > node2.freq)
                return 1;
            else if (node1.freq == node2.freq)
                return node2.word.compareTo(node1.word);
            else
                return -1;
        }
        public void swap(int pos1,int pos2){
            Node tmp = heap[pos1];
            heap[pos1] = heap[pos2];
            heap[pos2] = tmp;
        }
    }
    public class DynamicDataHeap{
        HashMap<Node,Integer> nodeIndexMap;//节点在堆中的位置
        HashMap<String,Node>  strNodeMap;//添加字符串时更新节点信息
        Node[] heap;
        int size;
        public DynamicDataHeap(int topK){
            nodeIndexMap = new HashMap<>();
            strNodeMap = new HashMap<>();
            heap = new Node[topK];
            size = 0;
        }
        public void add(String word){
            int pqIndex = -1;
            Node tmp = null;
            if (strNodeMap.containsKey(word)){
                tmp = strNodeMap.get(word);
                tmp.freq +=1;
                pqIndex = nodeIndexMap.get(tmp);
            }
            else{
                tmp = new Node(word,1);
                strNodeMap.put(word,tmp);
                nodeIndexMap.put(tmp,-1);
            }
            if (pqIndex == -1){
                if (size < heap.length){
                    heap[size] = tmp;
                    nodeIndexMap.put(tmp,size);
                    swim(size++);
                }
                else{
                    if (heap[0].freq < tmp.freq){
                        nodeIndexMap.put(heap[0],-1);
                        heap[0] = tmp;
                        nodeIndexMap.put(tmp,0);
                        sink(0,size);
                    }
                }
            }
            else{
                sink(pqIndex,size);
            }
        }
        public void printTopK() {
            System.out.println("TOP: ");
            for (int i = 0; i != heap.length; i++) {
                if (heap[i] == null) {
                    break;
                }
                System.out.print("Str: " + heap[i].word);
                System.out.println(" Times: " + heap[i].freq);
            }
        }
        public void sink(int begin,int size){
            while( 2 * begin+1 < size){
                int child1 = 2*begin+1;
                if (child1 + 1<size && heap[child1].freq > heap[child1+1].freq)
                    child1 +=1;
                if (heap[child1].freq < heap[begin].freq){
                    swap(begin,child1);
                    begin = child1;
                }
                else
                    break;
            }
        }
        public void swim(int size){
            while (size != 0){
                int parent = (size - 1)/2;
                if (heap[parent].freq <= heap[size].freq)
                    break;
                else{
                    swap(parent,size);
                    size = parent;
                }
            }
        }
        public void swap(int pos1,int pos2){
            nodeIndexMap.put(heap[pos1],pos2);
            nodeIndexMap.put(heap[pos2],pos1);
            Node tmp = heap[pos1];
            heap[pos1] = heap[pos2];
            heap[pos2] = tmp;
        }
    }
    public static void main(String[] args){
        String[] words={"i", "love", "leetcode", "i", "love", "coding"};
        FreqTopK ftk = new FreqTopK(words,2);
        Node[] p = ftk.staticDataHeap.getTopK();
        ftk.dynamicDataHeap.add("A");
        ftk.dynamicDataHeap.printTopK();
        ftk.dynamicDataHeap.add("B");
        ftk.dynamicDataHeap.add("B");
        ftk.dynamicDataHeap.printTopK();
        ftk.dynamicDataHeap.add("C");
        ftk.dynamicDataHeap.add("C");
        ftk.dynamicDataHeap.printTopK();
        ftk.dynamicDataHeap.add("kobe");
        ftk.dynamicDataHeap.add("kobe");
        ftk.dynamicDataHeap.add("kobe");
        ftk.dynamicDataHeap.printTopK();
       /* for (int i = 0;i<p.length;i++)
            System.out.println(p[i].word+"->"+p[i].freq);*/
    }

}
