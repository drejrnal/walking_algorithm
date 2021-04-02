package BackTracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-04-01 10:31
 */
public class DiffWaysAddParenthesees {

    public List<Integer> diffWaysCalcu(int start, int end, String expression){
        List<Integer> res = new LinkedList<>();
        for (int i = start;i<=end;i++){
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*'){
                List<Integer> left = diffWaysCalcu(start,i-1,expression);
                List<Integer> right = diffWaysCalcu(i+1,end,expression);
                for (int a:left) {
                    for (int b : right) {
                        switch (expression.charAt(i)){
                            case '+':
                                res.add(a+b);
                                break;
                            case '-':
                                res.add(a-b);
                                break;
                            case '*':
                                res.add(a*b);
                                break;
                        }
                    }
                }
            }
        }
        if (res.size() == 0)
            res.add(Integer.valueOf(expression.substring(start,end+1)));
        return res;
    }
    public List<Integer> diffWaysCalcuWithMem(String expression,HashMap<String,List<Integer>> cache){
        if (cache.containsKey(expression))
            return cache.get(expression);
        List<Integer> res = new ArrayList<>();
        for (int i = 0;i<expression.length();i++){
            if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*'){
                List<Integer> left = diffWaysCalcuWithMem(expression.substring(0,i),cache);
                cache.put(expression.substring(0,i),left);
                List<Integer> right = diffWaysCalcuWithMem(expression.substring(i+1),cache);
                cache.put(expression.substring(0,i),left);
                for (int a:left){
                    for (int b:right){
                        switch (expression.charAt(i)){
                            case '+':res.add(a+b);break;
                            case '-':res.add(a-b);break;
                            case '*':res.add(a*b);break;
                        }
                    }
                }
            }
        }
        if (res.size() == 0)
            res.add(Integer.valueOf(expression));
        return res;
    }
    public static void main(String[] args){
        DiffWaysAddParenthesees dwap = new DiffWaysAddParenthesees();
        String expression = "2*3-4*5";
        //ArrayList<Integer> ans = new ArrayList<>();
        HashMap<String,List<Integer>> cache = new HashMap<>();
        System.out.println(dwap.diffWaysCalcuWithMem(expression,cache));
        //System.out.println(ans);
    }
}
