package dynamicPrograme;

import org.omg.PortableInterceptor.DISCARDING;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author:毛翔宇
 * @Date 2019-04-18 16:47
 */
public class PathCoverAllNodes {
    public class State{
        int cover;
        int head;
        public State(int cover,int head){
            this.cover = cover;
            this.head = head;
        }
    }

    public int shortestPathAllNodes(int[][] graph){
        int N = graph.length;
        int[][] dist = new int[1<<N][N];
        Queue<State> queue = new LinkedList<>();
        for (int[] row: dist)
            Arrays.fill(row,N*N);
        for (int i = 0;i<N;i++) {
            queue.offer(new State(1 << i, i));
            dist[1<<i][i] = 0;
        }
        while(!queue.isEmpty()){
            State ele = queue.poll();
            int d = dist[ele.cover][ele.head];
            if ( ele.cover == (1<<N)-1 ) return d;
            for (int w:graph[ele.head]){
                int coverTmp = ele.cover | (1<<w);
                if (d+1 < dist[coverTmp][w]) {
                    dist[coverTmp][w] = d + 1;
                    queue.offer(new State(coverTmp, w));
                }
            }
        }
        throw null;
    }
    public static void main(String[] args){
        int[][] graph = {
                {1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}
        };
        PathCoverAllNodes pca = new PathCoverAllNodes();
        System.out.println(pca.shortestPathAllNodes(graph));

    }
}
