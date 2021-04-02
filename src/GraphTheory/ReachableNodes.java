package GraphTheory;


import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-06-05 14:06
 * leetcode 882
 * reachable node:a reachable node is a node that can be traveled to within M moves starting from node0.
 * 代码中注释的部分采用的是Java内置堆实现的lazy dijkstra
 * 最终提交采用的是定制化堆实现的eager dijkstra
 */
public class ReachableNodes {
    public class IndexMinPQ<Key extends Comparable<Key>>{
        public IndexMinPQ(int maxN){
            this.N = maxN;
            distance = (Key[]) new Comparable[maxN+1];
            pq = new int[maxN+1];
            qp = new int[maxN+1];
            size = 0;
            for (int i = 0;i<qp.length;i++)
                qp[i] = -1;
        }
        private int N;
        private int size;
        private Key[] distance;
        private int[] pq;
        private int[] qp; //qp[i]表示i节点在pq堆中的位置，即pq[qp[i]] = i；
        private void sink(int pos){
            int p;
            while(pos <= size){
                p = pos*2;
                if (p > size) break;
                if (p+1 <= size && distance[pq[p]].compareTo(distance[pq[p+1]])>0)
                    p++;
                if (distance[pq[pos]].compareTo(distance[pq[p]])>0)
                    swap(pos,p);
                else
                    break;
                pos = p;
            }
        }
        private void swim(int pos){
            int parent;
            while (pos > 1){
                parent = pos/2;
                if (distance[pq[parent]].compareTo(distance[pq[pos]])>0)
                    swap(parent,pos);
                else
                    break;
                pos = parent;
            }
        }
        private void swap(int pos1,int pos2){
            int tmp = pq[pos1];
            pq[pos1] = pq[pos2];
            pq[pos2] = tmp;
            qp[pq[pos1]] = pos1;
            qp[pq[pos2]] = pos2;
        }
        private boolean contains(int i){
            return qp[i] != -1;
        }
        private boolean isEmpty(){
            return size == 0;
        }
        public void add(int i,Key dist){
            distance[i] = dist;
            pq[++size] = i;
            qp[i] = size;
            swim(size);
        }
        public void change(int i,Key dist){
            distance[i] = dist;
            swim(qp[i]);
            sink(qp[i]);
        }
        public Node delMin(){
            int id = pq[1];
            Key dist = distance[id];
            swap(1,size--);
            sink(1);
            distance[id] = null;
            pq[size+1] = -1;
            qp[id] = -1;
            return new Node(id,dist);
        }
    }
    private class Edge{
        int weight; // 新增节点数量
        int either;
        int other;
        public Edge(int v, int w, int weight){
            this.weight = weight;
            this.either = v;
            this.other = w;
        }
        public int either(){
            return either;
        }
        public int other(int v){
            if (either == v)
                return other;
            else
                return either;
        }
    }
    public class Node<Key extends Comparable<Key>>{
        private int id;
        private Key weight;
        public Node(int id, Key weight){
            this.id = id;
            this.weight = weight;
        }
    }
    private int[] distTo;
    private LinkedList<Edge>[] graph;
    //private boolean[] marked;
    private HashMap<Integer,Integer> usedInfo;
    /*private PriorityQueue<Node> minPQ = new PriorityQueue<>(
            (a,b) -> Integer.compare(a.weight , b.weight)
    );*/

    private IndexMinPQ<Integer> pq;
    public int reachableNodes(int[][] edges, int M, int N){
        usedInfo = new HashMap<>();
        //marked = new boolean[N];
        graph = new LinkedList[N];
        distTo = new int[N];
        pq = new IndexMinPQ<>(N);
        for (int i = 0;i<N;i++) {
            graph[i] = new LinkedList<>();
            distTo[i] = M+1;
        }
        for (int[] ele:edges){
            Edge edge = new Edge(ele[0],ele[1],ele[2]);
            graph[ele[0]].add(edge);
            graph[ele[1]].add(edge);
        }
        distTo[0] = 0;
        //minPQ.offer(new Node(0,distTo[0]));
        pq.add(0,distTo[0]);
        int ans = 0;
        while(!pq.isEmpty()){
            //Node topPQ = minPQ.poll();
            Node topPQ = pq.delMin();
            int v = topPQ.id;
            //if (marked[v]) continue;
            ans +=1;
            visit(v,M,N);
        }
        for (int[] edge:edges){
            ans += Math.min(edge[2],usedInfo.getOrDefault(edge[0]*N+edge[1],0)
                                    +usedInfo.getOrDefault(edge[1]*N+edge[0],0));
        }
        return ans;
    }
    public void visit(int v,int M,int N){
        //marked[v] = true;
        for (Edge adj:graph[v]){
            int stepTogive = M - distTo[v];
            int w = adj.other(v);
            int res = Math.min(adj.weight,stepTogive);
            usedInfo.put(v*N+w,res);
            /*if (!marked[w]){
                if (distTo[w] > distTo[v]+adj.weight+1) {
                    distTo[w] = distTo[v]+adj.weight+1;
                    minPQ.offer(new Node(w,distTo[w]));
                }
            }*/
            if (distTo[w] > distTo[v]+adj.weight+1) {
                distTo[w] = distTo[v]+adj.weight+1;
                if (pq.contains(w))
                    pq.change(w,distTo[w]);
                else
                    pq.add(w,distTo[w]);
            }
        }
    }
    public void testGraph(){
        for (int i = 0;i<graph.length;i++){
            for (Edge edge:graph[i]) {
                System.out.print(edge.either() + " " + edge.other(edge.either) + " " + edge.weight);
                System.out.print('-');
            }
            System.out.println();
        }
    }
    public static void main(String[] args){
        ReachableNodes rn = new ReachableNodes();
        int[][] edges = {
                {2,4,2},{3,4,5},{2,3,1},{0,2,1},{0,3,5}
        };
        System.out.println(rn.reachableNodes(edges,14,5));
        rn.testGraph();
    }
}