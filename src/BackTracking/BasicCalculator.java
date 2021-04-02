package BackTracking;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author:毛翔宇
 * @Date 2019-04-01 16:34
 */
public class BasicCalculator {
    public int[] eval(char[] expression,int position){
        int[] locale = null;
        int pre = 0;
        Deque<String> val = new LinkedList<>();
        while(position < expression.length && expression[position]!=')'){
            if (expression[position] == ' ') {
                position++;
                continue;
            }
            else if (expression[position] >='0' && expression[position]<='9'){
                pre = pre *10 + expression[position++]-'0';
            }
            else if (expression[position]!='('){
                addNumber(val,pre);
                val.add(String.valueOf(expression[position++]));
                pre = 0;
            }
            else{
                locale = eval(expression,position+1);
                pre = locale[0];
                position = locale[1]+1;
            }
        }
        addNumber(val,pre);
        return new int[]{getNumber(val),position};
    }
    public int eval2(char[] expression,int[] position){
        int res = 0;
        int operator = 1;
        int pre = 0;
        int i = position[0];
        while (i<expression.length) {
            switch (expression[i]) {
                case '+':
                    res += operator * pre;
                    operator = 1;
                    pre = 0;
                    i++;
                    break;
                case '-':
                    res += operator * pre;
                    operator = -1;
                    pre = 0;
                    i++;
                    break;
                case '(':
                    position[0] = i+1;
                    res += operator * eval2(expression, position);
                    i = position[0];
                    break;
                case ')':
                    position[0] = i+1;
                    return res + operator * pre;
                case ' ':
                    i++;
                    continue;
                default:
                    pre = pre * 10 + expression[i] - '0';
                    i++;
            }
        }
        return res+operator * pre;
    }
    public void addNumber(Deque<String> value,int num){
        if (!value.isEmpty()){
            String top = value.peekLast();
            if (top.equals("*") || top.equals("/")){
                value.pollLast();
                int bottom = Integer.valueOf(value.pollLast());
                num = top.equals("*")?num*bottom:bottom/num;
            }
        }
        value.add(String.valueOf(num));
    }
    public int getNumber(Deque<String> value){
        int res = Integer.valueOf(value.pollFirst());
        while (!value.isEmpty()){
            String top = value.pollFirst();
            int cur = Integer.valueOf(value.pollFirst());
            if (top.equals("+"))
                res += cur;
            else
                res = res - cur;
        }
        return res;
    }
    public static void main(String[] args){
        BasicCalculator bc = new BasicCalculator();
        String exp = "2- (2+3)-(4-6)";
        int[] position = {0};
        //int[] res = bc.eval(exp.toCharArray(),0);
        int res = bc.eval2(exp.toCharArray(),position);
        System.out.println(res);
    }
}