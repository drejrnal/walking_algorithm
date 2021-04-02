package StringProblem;

import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019/1/20 11:23 AM
 */
public class SuffixArrayGen {
    private int[] group;
    private List<Integer> perm;
    private int[] rank;
    public class CustomeComparator implements Comparator<Integer> {
        private int[] group;
        private int t;
        public CustomeComparator(int[] _group,int _t){
            this.group = _group;
            this.t = _t;
        }
        @Override
        public int compare(Integer i1,Integer i2){
            if (group[i1] != group[i2])
                return group[i1] - group[i2];
            else
                return group[i1+t] - group[i2+t];
        }
    }
    public List<Integer> generateSuffixArray(int[] charArray){
        int size = charArray.length;
        perm = new ArrayList<>(size);
        group = new int[size+1];
        group[size] = -1;
        for (int i = 0;i<size;i++) {
            perm.add(i);
            group[i] = charArray[i];
        }
        int t = 1;
        while (t < size){
            CustomeComparator mycomp = new CustomeComparator(group,t);
            Collections.sort(perm,mycomp);
            t*=2;
            if (t >= size)
                break;
            int[] newgroup = new int[size+1];
            newgroup[size] = -1;
            newgroup[perm.get(0)] = 0;
            for (int i = 1;i<size;i++){
                if (mycomp.compare(perm.get(i-1),perm.get(i))<0)
                    newgroup[perm.get(i)] = newgroup[perm.get(i-1)]+1;
                else
                    newgroup[perm.get(i)] = newgroup[perm.get(i-1)];
            }
            group = newgroup;
        }
        return perm;
    }
    public int[] getRank(int[] s){
        List<Integer> permTmp = generateSuffixArray(s);
        int i = 0;
        rank = new int[s.length];
        for (Integer integer:permTmp){
            rank[integer] = i;
            i++;
        }
        return rank;
    }
    public static void main(String[] args){
        SuffixArrayGen sa = new SuffixArrayGen();
        //String s = "985628574";
        int[] s = {4,7,8,6,6,2,9,5,10,2,8,7,9,8,4};
        System.out.println(sa.generateSuffixArray(s));
        int[] rank = sa.getRank(s);
        for (int i = 0;i<rank.length;i++)
            System.out.print(rank[i]+" ");
    }
}
