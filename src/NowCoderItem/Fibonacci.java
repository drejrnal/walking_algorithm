package NowCoderItem;

public class Fibonacci {

    public static long recursiveFibo(int n) {
        if (n == 1 || n == 2 || n == 3)
            return n;
        else
            return recursiveFibo(n - 1) + recursiveFibo(n - 3);
    }

    //O(lgn)
    public static long advancedFibo(int n) {
        if (n == 1||n==2||n==3)
            return n;

        //int [][] res = new int[3][3];
        long[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};


        long[][] res = matrixPower(base, n - 3);

        return 3 * res[0][0] + 2 * res[1][0] + 1 * res[2][0];
    }

    public static long[][] matrixPower(long[][] _base, int factorial) {
        long[][] res = new long[_base.length][_base[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        for (; factorial != 0; factorial >>= 1) {
            if ((factorial & 1) == 1)
                res = matrixMuliple(res, _base);
            _base = matrixMuliple(_base, _base);
        }
        return res;
    }

    public static long[][] matrixMuliple(long[][] multiple, long[][] multiplier) {
        long[][] res = new long[multiple.length][multiplier[0].length];
        for (int i = 0; i < multiple.length; i++) {
            for (int j = 0; j < multiplier[0].length; j++) {
                for (int k = 0; k < multiplier.length; k++) {
                    res[i][j] += multiple[i][k] * multiplier[k][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] n = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        for (int i = 0;i<n.length;i++)
        {
            long exp = advancedFibo(n[i]);
            System.out.println(exp);
            if (recursiveFibo(n[i]) != exp)
            {
                System.out.println("Wrong BOOM");
                break;
            }
            System.out.println("==");
        }
        //System.out.println(advancedFibo(50));
        //System.out.println(advancedFibo(60));
    }
}
