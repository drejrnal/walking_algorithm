package QueryProblem;

/**
 * @Author:毛翔宇
 * @Date 2019-02-23 21:10
 */
public class DigitOneCounter {

    public int countDigitOne(int n){

        if(n <= 0)
            return 0;
        int len = getNumLen(n);
        if ( len == 1)
            return 1;
        int highBitSize = (int) Math.pow(10,len-1);
        int highBit = n /highBitSize;
        int highBitOnePos = highBit == 1?n%highBitSize+1:highBitSize;
        int remainBitOnePos = (int)Math.pow(10,len - 2)*(len-1)*highBit;
        return highBitOnePos+remainBitOnePos+countDigitOne(n%highBitSize);
    }
    public static int getNumLen(int n){
        int re = 0;
        while(n != 0){
            re ++;
            n/=10;
        }
        return re;
    }
    public static void main(String[] args){
        DigitOneCounter doc = new DigitOneCounter();
        System.out.println(doc.countDigitOne(13));
    }

}
