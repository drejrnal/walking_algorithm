package GraphTheory;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-04-26 17:41
 */
public class FriendsCirceles {

    //private List<Integer>[] graph;
    private int N;
    private int id;
    private boolean[] marked;
    private int[] link;
    private int[] size;
    public int findCircleNum(int[][] num){
        N = num.length;
        marked = new boolean[N];
        for (int i = 0;i<N;i++){
            if (!marked[i]) {
                dfs(i,num);
                id++;
            }
        }
        return id;
    }
    public void dfs(int v,int[][] graph){
        marked[v] = true;
        for (int i = 0;i<N;i++){
            if (!marked[i] && graph[v][i] == 1)
                dfs(i,graph);
        }
    }
    public int UFFindCycle(int[][] graph){
        N = graph.length;
        id = N;
        link = new int[N];
        size = new int[N];
        for (int i = 0;i<N;i++){
            link[i] = i;
            size[i] = 1;
        }
        for (int i = 0;i<N;i++){
            for (int j = 0;j<N;j++){
                if (graph[i][j] == 1)
                    union(i,j);
            }
        }
        return id;
    }
    public int find(int v){
        while(v != link[v])
            v = link[v];
        return v;
    }
    public void union(int p, int q){
        int proot = find(p);
        int qroot = find(q);
        if (proot == qroot)
            return;
        if (size[proot] > size[qroot]) {
            link[qroot] = proot;
            size[proot]+=size[qroot];
        }
        else{
            link[qroot] = proot;
            size[qroot] += size[proot];
        }
        id --;
    }



    /*public int findCircleNum(int[][] num){
        N = num.length;
        marked = new boolean[N];
        graph = new LinkedList[N];
        for (int i = 0;i < N;i++){
            graph[i] = new LinkedList<>();
            for (int j = 0;j<N;j++){
                if (j!=i && num[i][j] == 1)

                    graph[i].add(j);
            }
        }
        for (int i = 0;i<N;i++){
            if (!marked[i]) {
                dfs(i);
                id++;
            }
        }
        return id;
    }

    public void dfs(int v){
        marked[v] = true;
        for (int w:graph[v]){
            if (!marked[w])
                dfs(w);
        }
    }*/
    public static void main(String[] args){
        int[][] matrix = {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };
        FriendsCirceles fc = new FriendsCirceles();
        fc.UFFindCycle(matrix);
        System.out.println(fc.id);
    }

}
