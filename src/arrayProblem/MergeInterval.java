package arrayProblem;


import java.util.*;

/**
 * @Author:毛翔宇
 * @Date 2019-02-26 21:53
 */
public class MergeInterval {
    public static class Interval{
        int start;
        int end;
        Interval(){
            start = 0; end = 0;
        }
        Interval(int s,int e){
            start = s;
            end = e;
        }
    }
    public class intervalComp implements Comparator<Interval> {

        @Override
        public int compare(Interval i1,Interval i2){
            if (i1.start != i2.start)
                return i1.start - i2.start;
            else
                return i1.end - i2.end;
        }
    }
    public List<Interval> merge(List<Interval> intervalList){
        List<Interval> res = new ArrayList<>();
        if(intervalList.isEmpty())
            return res;
        int length = intervalList.size();
        Interval[] intervals = new Interval[length];
        int[] ends = new int[length];
        int k = 0;
        for (Interval interval:intervalList) {
            intervals[k] = interval;
            ends[k] = interval.end;
            k++;
        }
        Arrays.sort(intervals,new intervalComp());
        Arrays.sort(ends);
        int leftBorder = intervals[0].start,rightBorder= intervals[0].end;
        int i= 0,next = 0;
        while (rightBorder != ends[intervals.length-1]){
            while(isOverlap(intervals[i],intervals[next]))
                next++;
            while (next<length && isIntersect(intervals[i],intervals[next])) {
                rightBorder = intervals[next].end;
                i = next;
                next+=1;
            }
            if (next<length && isExclude(intervals[i],intervals[next])) {
                res.add(new Interval(leftBorder,rightBorder));
                leftBorder = intervals[next].start;
                rightBorder = intervals[next].end;
                i = next;
                next+=1;
            }
        }
        res.add(new Interval(leftBorder,rightBorder));
        return res;
    }
    public List<Interval> merge2(List<Interval> intervalList){
        int length = intervalList.size();
        List<Interval> res = new LinkedList<>();
        int[] start = new int[length],end = new int[length];
        int k = 0;
        for (Interval interval:intervalList){
            start[k] = interval.start;
            end[k] = interval.end;
            k++;
        }
        int leftBorder = start[0];
        int rightBorder = end[0];
        for (Interval interval:intervalList){
            if (rightBorder >= interval.start){
                rightBorder = Math.max(rightBorder,interval.end);
            }
            else{
                res.add(new Interval(leftBorder,rightBorder));
                leftBorder = interval.start;
                rightBorder = interval.end;
            }
        }
        res.add(new Interval(leftBorder,rightBorder));
        return res;
    }
    public List<Interval> insert(List<Interval> intervals,Interval _interval){
        int j = 0;
        for (Interval interval:intervals){
            if (interval.start < _interval.start)
                j++;
            else if (interval.start == _interval.start && interval.end <= _interval.end){
                j++;
            }
            else
                break;
        }
        intervals.add(j,_interval);
        return merge2(intervals);
    }
    public List<Interval> insert2(List<Interval> intervals,Interval _interval){
        int i = 0,length = intervals.size();
        List<Interval> res = new LinkedList<>();
        while(i < length && intervals.get(i).end < _interval.start) {
            res.add(intervals.get(i));
            i++;
        }
        int leftBorder = _interval.start,rightBorder = _interval.end;
        while(i < length && intervals.get(i).start <= _interval.end){
            leftBorder = Math.min(leftBorder,intervals.get(i).start);
            rightBorder = Math.max(rightBorder,intervals.get(i).end);
            i++;
        }
        res.add(new Interval(leftBorder,rightBorder));
        while (i < length) {
            res.add(intervals.get(i));
            i++;
        }
        return res;
    }
    public boolean isIntersect(Interval i1,Interval i2){
        return i2.start <= i1.end && !isOverlap(i1,i2);
    }
    public boolean isOverlap(Interval i1,Interval i2){
        return i2.end <= i1.end;
    }
    public boolean isExclude(Interval i1,Interval i2){
        return i2.start > i1.end;
    }
    public static void main(String[] args){
        List<Interval> intervals = new LinkedList<>();
        intervals.add(new Interval(0,0));
        intervals.add(new Interval(1,2));

        intervals.add(new Interval(2,4));
        intervals.add(new Interval(3,3));
        intervals.add(new Interval(5,5));
        intervals.add(new Interval(5,6));
        intervals.add(new Interval(5,6));
        intervals.add(new Interval(4,6));

        //intervals.add(new Interval(10,16));
        MergeInterval mi = new MergeInterval();
        List<Interval> res = mi.insert2(intervals,new Interval(0,2));
        //List<Interval> res = mi.insert(intervals,new Interval(0,2));
        for (Interval interval:res){
            System.out.println(interval.start+"-"+interval.end);
        }
    }
}
