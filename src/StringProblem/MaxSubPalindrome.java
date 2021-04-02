package StringProblem;

/**
 * @Author:毛翔宇
 * @Date 2019-02-13 17:08
 */
public class MaxSubPalindrome {

    public static char[] adjustPattern(String pattern) {
        char[] cpattern = pattern.toCharArray();
        char[] dpattern = new char[cpattern.length * 2 + 1];
        int j = 0;
        for (int i = 0; i < dpattern.length; i++) {
            dpattern[i] = (i & 1) == 0 ? '#' : cpattern[j++];
        }
        return dpattern;
    }
    public static String getMaxPalinSubString(String pattern){
        char[] charArray = adjustPattern(pattern);
        int[] radius = new int[charArray.length];
        int r_Border = 0;
        int center = 0;
        int index = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0;i<charArray.length;i++){
            radius[i] = r_Border > i ? Math.min(radius[2*center-i],r_Border - i):1;
            while(radius[i] + i < charArray.length && i - radius[i] >= 0){
                if (charArray[i + radius[i]] == charArray[i - radius[i]]){
                    radius[i] ++;
                }
                else
                    break;
            }
            if ( i + radius[i] > r_Border){
                center = i;
                r_Border = i + radius[i];
            }
            if (radius[i] > max){
                max = radius[i];
                index = i;
            }
        }
        return pattern.substring((index-max+1)/2,(index-1+max)/2);
    }
    public static void main(String[] args) {
        //String str1 = "abc12343221ab";
        String str1 = "abc1234321ab";
        //System.out.println(adjustPattern(str1));
        //int[] p = Manacher(str1);
        //System.out.println(restore(adjustPattern(str1), p[0],p[1]));
        System.out.println(getMaxPalinSubString(str1));
    }

}
