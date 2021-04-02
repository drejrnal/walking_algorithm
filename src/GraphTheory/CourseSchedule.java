package GraphTheory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Author:毛翔宇
 * @Date 2019-04-17 16:02
 */
public class CourseSchedule {

    private List<Integer>[] graph;
    private Stack<Integer> reversePostOrder;
    private boolean[] marked;
    private boolean[] onStack;
    private boolean hasCycle;

    public boolean canFinish(int numCourses, int[][] prerequsites) {
        graph = new List[numCourses];
        for (int[] edge : prerequsites){
            if (graph[edge[1]] == null)
                graph[edge[1]] = new LinkedList<>();
            graph[edge[1]].add(edge[0]);
        }
        marked = new boolean[numCourses];
        onStack = new boolean[numCourses];
        for (int v = 0;v<numCourses;v++) {
            if (!marked[v])
                dfs(graph, v);
        }
        return !hasCycle;
    }

    public void dfs(List<Integer>[] graph, int v) {
        marked[v] = true;
        onStack[v] = true;
        if (graph[v]!=null) {
            for (int w : graph[v]) {
                if (hasCycle) return;
                else if (!marked[w]) dfs(graph, w);
                else if (onStack[w])
                    hasCycle = true;
            }
        }
        //reversePostOrder.add(v);
        onStack[v] = false;
    }

    public int[] findOrder(int numCourses, int[][] prerequsites) {

        if (!canFinish(numCourses, prerequsites))
            return new int[]{};
        else {
            marked = new boolean[numCourses];
            int[] res = new int[numCourses];
            reversePostOrder = new Stack<>();
            for (int v = 0;v<numCourses;v++)
                if (!marked[v])
                    dfsOrder(graph, v);
            int index = 0;
            while (!reversePostOrder.isEmpty()) {
                onStack[reversePostOrder.peek()] = true;
                res[index++] = reversePostOrder.pop();
            }
            for (int i = 0; i < res.length; i++) {
                if (!onStack[i])
                    res[index++] = i;
            }
            return res;
        }
    }

    public void dfsOrder(List<Integer>[] graph, int v) {
        marked[v] = true;
        if (graph[v]!=null)
            for (int w : graph[v])
                if (!marked[w]) dfsOrder(graph, w);

        reversePostOrder.push(v);
    }

    public static void main(String[] args) {
        int[][] pre = {
                {1,0}
        };
        CourseSchedule cs = new CourseSchedule();
        int[] ans = cs.findOrder(5, pre);
        for (int i = 0; i < ans.length; i++)
            System.out.println(ans[i]);
    }
}
