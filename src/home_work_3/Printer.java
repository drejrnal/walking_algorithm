package home_work_3;

public class Printer {

    public void assignColumn(int[] _re, int[] vector, int i, int column, boolean _reverse) {
        if (!_reverse) {
            for (int index = 0, c = 0; c < column; c++, index++) {
                _re[column * i + index] = vector[c];
            }

        } else {
            for (int index = 0, c = column - 1; c >= 0; c--, index++)
                _re[column * i + index] = vector[c];
        }
    }

    public int[] printMatrix(int[][] mat, int n, int m) {
        int[] re = new int[n * m];
        boolean reverse = false;
        for (int i = 0; i < n; i++) {
            assignColumn(re, mat[i], i, m, reverse);
            reverse = !reverse;

        }
        return re;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {5, 6, 7}, {9, 10, 11}};
        Printer p = new Printer();
        int [] re = p.printMatrix(matrix, matrix.length, matrix[0].length);
        for(int i = 0;i<re.length;i++)
            System.out.println(re[i]);
    }
}
