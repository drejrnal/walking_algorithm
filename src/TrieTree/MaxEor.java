package TrieTree;

import java.util.BitSet;

/**
 * @Author:毛翔宇
 * @Date 2019/3/2 10:37 AM
 */
public class MaxEor {
    public class EorTrieTree {
        public class Node {
            Node[] children = new Node[2];
        }
        private Node head = new Node();
        public void addNumber(int number) {
            Node traverse = head;
            for (int move = 31; move >= 0; move--) {
                int bit = (number >> move) & 1;
                traverse.children[bit] = (traverse.children[bit] == null) ? new Node() : traverse.children[bit];
                traverse = traverse.children[bit];
            }
        }
        public int maxEor(int number) {
            int res = 0;
            Node traverse = head;
            for (int move = 31; move >= 0; move--) {
                int bit = (number >> move) & 1;
                int expect = (move == 31) ? bit : (bit ^ 1);
                int real = (traverse.children[expect] == null) ? (expect ^ 1) : expect;
                res |= ((bit ^ real) << move);
                traverse = traverse.children[real];
            }
            return res;
        }
    }
    public int getMaxEor(int[] arr){
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int eorIter = 0;
        int res =Integer.MIN_VALUE;
        EorTrieTree ett = new EorTrieTree();
        //System.out.println(ett.head.children[0]);
        //因为任何数同0异或是其本身，初始添0
        ett.addNumber(0);
        for (int i = 0;i< arr.length;i++) {
            eorIter ^= arr[i];
            int partialMax = ett.maxEor(eorIter);
            ett.addNumber(eorIter);
            res = Math.max(res,partialMax);
        }
        return res;
    }


    public static void testBitSet(int num1,int num2){

        BitSet bitSet = new BitSet();
        BitSet bitSet1 = new BitSet();
        System.out.println("1:"+bitSet.length());
        System.out.println("2:"+bitSet.size());
        for (int i = 31;i>=0;i--){
            int f = (num1>>i)&1;
            int q = (num2>>i)&1;
            bitSet.set(i,f==1);
            bitSet1.set(i,q==1);
        }
        System.out.println("3:"+bitSet.get(0)+bitSet.length());
        int re1 = 0,re2=0;
        for (int i = 0;i<bitSet.length();i++){
            re1 += bitSet.get(i)?1<<i:0;
            re2 += bitSet1.get(i)?1<<i:0;
        }
        System.out.println("4:num1"+re1);
        System.out.println("5:num2"+re2);
    }
    // for test
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int eor = 0;
            for (int j = i; j < arr.length; j++) {
                eor ^= arr[j];
                max = Math.max(max, eor);
            }
        }
        return max;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        MaxEor maxEor = new MaxEor();
        int[] arr = {1,2,4};
        System.out.println(maxEor.getMaxEor(arr));
    }
}


