package classicAlgo;

public class Manacher {

    public static char[] adjustPattern(String pattern) {
        char[] cpattern = pattern.toCharArray();
        char[] dpattern = new char[cpattern.length * 2 + 1];
        int j = 0;
        for (int i = 0; i < dpattern.length; i++) {
            dpattern[i] = (i & 1) == 0 ? '#' : cpattern[j++];
        }
        return dpattern;
    }
    public static int manacher(String pattern) {
        int rBorder = -1;
        int center = 0;
        int index = -1;
        char[] dpattern = adjustPattern(pattern);
        int[] prate = new int[dpattern.length];
        int i = 0;
        int max = Integer.MIN_VALUE;
        while (i < dpattern.length) {
            prate[i] = (rBorder > i) ? Math.min(rBorder - i, prate[2 * center - i]) : 1;
            while (i + prate[i] < dpattern.length && i - prate[i] >= 0) {
                if (dpattern[i + prate[i]] == dpattern[i - prate[i]]) {
                    prate[i]++;
                } else
                    break;
            }
            if (i + prate[i] > rBorder) {
                rBorder = i + prate[i];
                center = i;
            }
            if(prate[i] > max)
            {
                index = i;
                max = prate[i];
            }
            i++;

        }

        //rBorder始终是线性增加，我犯的错误是返回center位置对应的回文长度，这是错误的，因为在遍历过程中，随着i的右移，
        //center也会不断增加，最后的center对应的只是rBorder到最后时对应的center，此时它不一定是最长回文子串的中心位置。
        return max-1;
        //return new int[]{index,max};
    }

    public static char[] restore(char[] dpattern, int center, int radius){
        int begin = center - radius+1;
        int index = 0;
        char[] res = new char[radius-1];
        while(begin < center+radius){
            if(dpattern[begin] != '#')
                res[index++] = dpattern[begin];
            begin++;
        }
        return res;
    }
    public static void main(String[] args) {
        //String str1 = "abc12343221ab";
        String str1 = "abc1234321ab";
        //System.out.println(adjustPattern(str1));
        //int[] p = Manacher(str1);
        //System.out.println(restore(adjustPattern(str1), p[0],p[1]));
        System.out.println(manacher(str1));
    }
}
