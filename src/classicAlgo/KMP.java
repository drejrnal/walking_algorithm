package classicAlgo;

public class KMP {

    public static int getindexofpattern(String resource, String pattern) {
        if (pattern.length() == 0)
            return -1;
        char[] source = resource.toCharArray();
        char[] cpattern = pattern.toCharArray();
        int master = 0;
        int Y = 0;
        int[] next = getNextArray(cpattern);

        while (master < source.length && Y < cpattern.length) {
            if (source[master] == cpattern[Y]) {
                master++;
                Y++;
            } else if (Y == 0) {
                master++;
            } else
                Y = next[Y];
        }

        //return X == source.length ? -1:X - Y; 错误的返回，因为如果cpattern一直同source最后匹配，source就会遍历到最后，
        //核心是X始终线性增加，而Y只有能被匹配才会遍历到最后，Y增加（匹配）、减少（不匹配）不变（为0直到匹配source其中一个字符）
        return Y == cpattern.length ? master - Y:-1;
    }

    public static int[] getNextArray(char[] pattern) {

        int[] next = new int[pattern.length];
        next[0] = -1;
        next[1] = 0;
        int q = 0;
        for (int i = 2; i < pattern.length; i++) {

            if (pattern[i - 1] == pattern[q]) {
                q = q + 1;
                next[i] = q;
            } else if (q == 0)
                next[i] = 0;
            else
                q = next[q];
        }


        return next;
    }
    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String match = "ccc";
        System.out.println(getindexofpattern(str, match));

    }
}
