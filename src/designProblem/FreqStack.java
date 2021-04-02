package designProblem;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author:毛翔宇
 * @Date 2019-03-07 14:58
 */
public class FreqStack {
    private HashMap<Integer, Stack<Integer>> freqStackMap;//相同频率记录元素在栈中的顺序
    private HashMap<Integer,Integer> numFreqMap;          //元素的频率表
    private int maxFreq;
    public FreqStack(){
        maxFreq = Integer.MIN_VALUE;
        freqStackMap = new HashMap<>();
        numFreqMap = new HashMap<>();
    }
    public void push(int x){
        int cnt = numFreqMap.getOrDefault(x,0)+1;
        numFreqMap.put(x,cnt);
        maxFreq = Math.max(maxFreq,cnt);
        if (!freqStackMap.containsKey(cnt))
            freqStackMap.put(cnt,new Stack<>());
        freqStackMap.get(cnt).push(x);
    }

    public int pop(){
        Stack<Integer> tmp = freqStackMap.get(maxFreq);
        int res = tmp.pop();
        numFreqMap.put(res,maxFreq-1);
        if (tmp.isEmpty())
            maxFreq -=1;
        return res;
    }
    public static void main(String[] args){
        FreqStack fs = new FreqStack();
        fs.push(5);
        fs.push(7);
        fs.push(5);
        fs.push(7);
        fs.push(4);
        fs.push(5);
        System.out.println(fs.pop());
        fs.push(4);
        System.out.println(fs.pop());
        System.out.println(fs.pop());
        System.out.println(fs.pop());
    }
}
