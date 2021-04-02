package GraphTheory;


import java.util.HashMap;

/**
 * @Author:毛翔宇
 * @Date 2018/12/10 6:39 AM
 */
public class Dijkstra {
    public static HashMap<GraphNode,Integer> dijkstra(GraphNode graphNode, int size){
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpadateOrIgnore(graphNode,0);
        HashMap<GraphNode,Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()){
            NodeRecord nodeRecord = nodeHeap.popSmallestWeight();
            GraphNode curNode = nodeRecord.graphNode;
            int distance = nodeRecord.weight;
            for (Edge edge: curNode.edges){
                nodeHeap.addOrUpadateOrIgnore(edge.to,distance + edge.weight);
            }
            result.put(curNode,distance);
        }
        return result;
    }
    /*
    在将nodes[size-1]=null后，原来节点会被自动回收，因此需要在被删除前拷贝节点记录信息
    */
    public static class NodeRecord{
        private int weight;
        private GraphNode graphNode;
        NodeRecord(GraphNode graphNode,int val){
            this.graphNode = graphNode;
            this.weight = val;
        }
    }

    //customized Heap
    public static class NodeHeap{
        private GraphNode[] nodes;
        private HashMap<GraphNode,Integer> heapIndexMap;
        private HashMap<GraphNode,Integer> distanceMap;
        private int size;
        //Initializing
        public NodeHeap(int size){
            nodes = new GraphNode[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            this.size = 0;
        }
        public void addOrUpadateOrIgnore(GraphNode graphNode,int up_distance){
            if (!isEntered(graphNode)){
                nodes[size] = graphNode;
                heapIndexMap.put(graphNode,size);
                heapInsert(size++);
            }
            else if (isExisting(graphNode)){
                distanceMap.put(graphNode,Math.min(distanceMap.get(graphNode),up_distance));
                heapInsert(heapIndexMap.get(graphNode));
            }
        }
        public NodeRecord popSmallestWeight(){
            NodeRecord result = new NodeRecord(nodes[0],distanceMap.get(nodes[0]));
            swap(0,size-1);
            distanceMap.remove(nodes[size-1]);
            heapIndexMap.put(nodes[size-1],-1);
            nodes[--size] = null;
            heapify(size);
            return result;
        }
        /*
        help method
        to check if the nodes[] is empty
        using in main()
         */
        public boolean isEmpty(){
            return size == 0;
        }

        /*
        help method
        to check the node to add is in the heap or had been in the heap
         */
        public boolean isEntered(GraphNode graphNode){
            return heapIndexMap.containsKey(graphNode);
        }
        public boolean isExisting(GraphNode graphNode){
            return heapIndexMap.containsKey(graphNode) && heapIndexMap.get(graphNode) != -1;
        }
        /*
        help Method
        sink()
        heapInsert()
        swap()
         */

        public  void heapify(int size){
            int traverse = 0;
            while (traverse < size){
                int prior = traverse;
                int left = 2*traverse+1,right = 2*traverse+2;
                if (left < size && (distanceMap.get(nodes[traverse]) > distanceMap.get(nodes[left]))){
                    traverse = left;
                }
                if (right < size && (distanceMap.get(nodes[traverse]) > distanceMap.get(nodes[right]))){
                    traverse = right;
                }
                if (prior != traverse)
                    swap(prior,traverse);
                else
                    break;
            }
        }
        public void heapInsert(int index){
            int traverse =  index;
            int parent = (traverse-1)/2;
            while(parent >= 0 && distanceMap.get(nodes[parent]) > distanceMap.get(nodes[traverse])){
                swap(parent,traverse);
                traverse = parent;
            }
        }
        public void swap(int index1,int index2){
            heapIndexMap.put(nodes[index1],index2);
            heapIndexMap.put(nodes[index2],index1);
            GraphNode tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

}
