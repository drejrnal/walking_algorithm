package classicAlgo;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-02-24 10:36
 */
public class NQueensProblem {


    public int getSolutions(int n){
        if (n <1 || n > 32)
            return 0;
        int endState = n == 32?-1:(1<<n)-1;
        return totalNQueenSolutions(endState,0,0,0);
    }

    public int totalNQueenSolutions(int endState,int columnState,int leftState,int rightState){
        if (columnState == endState)
            return 1;
        int total = 0;
        int availablePosition = endState & ~(columnState | leftState | rightState);
        while(availablePosition != 0) {
            int endianDigitOne = availablePosition & (-availablePosition);

            total += totalNQueenSolutions(endState, columnState | endianDigitOne,
                    (leftState | endianDigitOne) << 1, (rightState | endianDigitOne) >> 1);

            availablePosition ^= endianDigitOne;
        }
        return total;
    }


    public  List<List<String>> NQueens(int n){
        List<List<String>> result = new LinkedList<List<String>>();
        int[] record = new int[n];
        printNQueues(0,record,n,result);
        return result;
    }

    public void printNQueues(int row,int[] record,int n,List<List<String>> re){
        if (row == n ){
            List<String> tmp = new LinkedList<>();
            for (int i =0;i < n;i++){
                StringBuffer sb = new StringBuffer();
                for (int j = 0 ;j<n;j++){
                    if (record[i] == j)
                        sb.append("Q");
                    else
                        sb.append(".");
                }
                tmp.add(sb.toString());
            }
            re.add(tmp);
            return;
        }
        for (int j = 0;j<n;j++){
            if (isValid(row,j,record)){
                record[row] = j;
                printNQueues(row+1,record,n,re);

            }
        }
        /*

         */
    }

    public boolean isValid(int i,int pos,int[] record){
        for (int k = 0;k<i;k++) {
            if (record[k] == pos || Math.abs(i - k) == Math.abs(pos - record[k])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        NQueensProblem nqp = new NQueensProblem();
        System.out.println(nqp.getSolutions(5));
        System.out.println(nqp.NQueens(5));
    }
}
