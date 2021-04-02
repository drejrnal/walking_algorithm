package designProblem;

import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2018/12/30 9:42 PM
 * @Date 2018/1/1 1:00 PM leetcode通过
 */
public class SkyLine {

    public class Node{
        int xStart;
        int height;
        boolean isAdded;
        public Node(int x,int h,boolean isAdded){
            this.xStart = x;
            this.height = h;
            this.isAdded = isAdded;
        }
    }
    public static class myComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1,Node o2){
            if (o1.xStart != o2.xStart)
                return o1.xStart - o2.xStart;
            else
                return o1.isAdded == true?-1:1;
        }
    }
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> result = new ArrayList<>();
        TreeMap<Integer,Integer> heightToFreq = new TreeMap<>();
        TreeMap<Integer,Integer> xToHeight = new TreeMap<>();
        Node[] optSequence = new Node[buildings.length*2];
        for (int i = 0;i<buildings.length;i++){
            int xs = buildings[i][0];
            int xe = buildings[i][1];
            int height = buildings[i][2];
            optSequence[2*i] = new Node(xs,height,true);
            optSequence[2*i+1] = new Node(xe,height,false);
        }
        Arrays.sort(optSequence,new myComparator());
        int maxTmp = Integer.MIN_VALUE;
        for (int i = 0;i<optSequence.length;i++){
            if (optSequence[i].isAdded){
                if (heightToFreq.containsKey(optSequence[i].height))
                    heightToFreq.put(optSequence[i].height,heightToFreq.get(optSequence[i].height)+1);
                else
                    heightToFreq.put(optSequence[i].height,1);
                if (heightToFreq.lastKey() > maxTmp){
                    maxTmp = heightToFreq.lastKey();
                    xToHeight.put(optSequence[i].xStart,optSequence[i].height);
                }
            }
            else{
                heightToFreq.put(optSequence[i].height,heightToFreq.get(optSequence[i].height)-1);
                if (heightToFreq.get(optSequence[i].height) == 0)
                    heightToFreq.remove(optSequence[i].height);
                if (heightToFreq.isEmpty()){
                    maxTmp = 0;
                    xToHeight.put(optSequence[i].xStart,0);
                }
                else if(maxTmp != heightToFreq.lastKey()){
                    maxTmp = heightToFreq.lastKey();
                    xToHeight.put(optSequence[i].xStart,maxTmp);
                }
            }
        }
        for (Map.Entry entry:xToHeight.entrySet()){
            int[] inner = new int[2];
            inner[0] =(int) entry.getKey();
            inner[1] =(int) entry.getValue();
            result.add(inner);
        }
        return result;
    }
}
