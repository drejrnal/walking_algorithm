package HeapProblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Author:毛翔宇
 * @Date 2019/1/19 10:51 AM
 */
public class ProjectManage {

    public static class ProjectComp implements Comparator<Project>{
        @Override
        public int compare(Project p1,Project p2){
            if (p1.priority != p2.priority)
                return p1.priority - p2.priority;
            else if (p1.cost != p2.cost)
                return p1.cost - p2.cost;
            else
                return p1.pmNum - p2.pmNum;
        }
    }

    public static class Project{
        int pmNum;
        int priority;
        int start;
        int cost;
        int indice;
        public Project(int _indice,int _pmNum,int _start,int _priority,int _cost){
            this.indice = _indice;
            this.pmNum = _pmNum;
            this.priority = _priority;
            this.start = _start;
            this.cost  = _cost;
        }
    }
    public class IndexMinPQ{
        private int heapSize;
        private int[] pq;
        private int[] qp;//qp[pq[i]] = pq[qp[i]] = i
        private Project[] projects;
        List<PriorityQueue<Project>> pmPqList;
        public IndexMinPQ(int pmSize){
            heapSize = 0;
            projects = new Project[pmSize+1];
            pq = new int[pmSize+1];
            qp = new int[pmSize+1];
            for (int i = 1;i<=pmSize;i++)
                qp[i] = -1;
            pmPqList = new ArrayList<>();
            for (int i = 0;i<pmSize;i++){
                pmPqList.add(new PriorityQueue<>(new ProjectComp()));
            }
        }
        public boolean isEmpty(){
            return heapSize == 0;
        }
        public void add(Project parProject){
            int num = parProject.pmNum;
            PriorityQueue<Project> pmPq = pmPqList.get(num-1);
            if (pmPq.isEmpty()){
                pmPq.add(parProject);
                heapSize++;
                projects[num] = parProject;
                pq[heapSize] = num;
                qp[num] = heapSize;
                swim(heapSize);
            }
            else{
                pmPq.add(parProject);
                Project peekProject = pmPq.peek();
                projects[num] = peekProject;
                swim(qp[num]);
                sink(qp[num]);
            }
        }
        public Project pop(){
            Project priorProject = projects[pq[1]];
            int num = priorProject.pmNum;//assert pq[1] = num
            pmPqList.get(num-1).poll();
            if (pmPqList.get(num-1).isEmpty()){
                exch(1,heapSize--);
                qp[num] = -1;
                projects[num] = null;
            }
            else
                projects[num] = pmPqList.get(num-1).peek();//assert pq[1] = num,qp[num] = 1
            sink(1);
            return priorProject;
        }
        /*
        dfs method
         */
        public void exch(int indice1,int indice2){
            int tmp = pq[indice1];
            pq[indice1] = pq[indice2];
            pq[indice2] = tmp;
            qp[pq[indice1]] = indice1;
            qp[pq[indice2]] = indice2;
         }
        public void sink(int indice){
            while(2 * indice<=heapSize){
                int j = 2*indice;
                if (j+1<=heapSize && greater(j,j+1)) j++;
                if (!greater(indice,j)) break;
                exch(indice,j);
                indice = j;
            }
        }
        public void swim(int indice){
            while(indice/2 > 0 && greater(indice/2,indice)){
                exch(indice/2,indice);
                indice = indice/2;
            }
        }
        public boolean greater(int indice1,int indice2){
            if (projects[indice1].cost != projects[indice2].cost)
                return projects[indice1].cost - projects[indice2].cost >0;
            else
                return indice1 - indice2>0;
        }
    }
    public class TimeComp implements Comparator<Project>{
        @Override
        public int compare(Project p1,Project p2){
            return p1.start - p2.start;
        }
    }
    public  int[] finishProject(int pNum,int sde,int[][] programs){
        int[] ans = new int[programs.length];
        IndexMinPQ indexMinPQ = new IndexMinPQ(pNum);
        PriorityQueue<Project> timeBasedPQ = new PriorityQueue<>(new TimeComp());
        for (int i = 0;i<programs.length;i++){
            Project program = new Project(i,programs[i][0],programs[i][1],programs[i][2],programs[i][3]);
            timeBasedPQ.add(program);
        }
        PriorityQueue<Integer> programmerBasedPQ = new PriorityQueue<>();
        for (int i = 0;i<sde;i++)
            programmerBasedPQ.add(1);
        int finish = 0;
        while(finish != ans.length){
            int prepareTime = programmerBasedPQ.poll();
            while(!timeBasedPQ.isEmpty()){
                if (timeBasedPQ.peek().start > prepareTime)
                    break;
                else{
                    indexMinPQ.add(timeBasedPQ.poll());
                }
            }
            if (indexMinPQ.isEmpty())
                programmerBasedPQ.add(timeBasedPQ.peek().start);
            else {
                Project tmp = indexMinPQ.pop();
                ans[tmp.indice] = prepareTime+tmp.cost;
                programmerBasedPQ.add(ans[tmp.indice]);
                finish++;
            }
        }
        return ans;
    }
    public static void main(String[] args){
        int[][] programs = {{1,1,1,2},
                {1,2,1,1},
                {1,3,2,6},
                {2,1,1,2},
                {2,3,5,5}};
        /*Project[] projects = new Project[programs.length];
        for (int i = 0;i<projects.length;i++){
            Project program = new Project(i,programs[i][0],programs[i][1],programs[i][2],programs[i][3]);
            projects[i] = program;
        }*/
        int sde = 2;
        int pm = 2;
        ProjectManage pjm = new ProjectManage();
        int[] ans = pjm.finishProject(pm,sde,programs);
        for (int i = 0;i<ans.length;i++)
            System.out.println(ans[i]);
    }


}
