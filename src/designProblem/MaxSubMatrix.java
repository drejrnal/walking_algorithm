package designProblem;



import java.util.Stack;

public class MaxSubMatrix {


    public static int[] geneHistogram(int[][] matrix){
        int[] histoArray = new int[matrix[0].length];
        for (int i = 0;i<matrix.length;i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                histoArray[j] = (matrix[i][j] == 0) ? 0 : histoArray[j] + 1;
            }
        }
        return histoArray;
    }


    public static int getMaxSubSize(int[] seriel) {
        if (seriel.length == 0)
            return 0;
        Stack<Integer> monotonousStack = new Stack<>();
        monotonousStack.push(0);
        int maxSubscale = Integer.MIN_VALUE;
        for (int i = 1; i < seriel.length; i++) {
            while (!monotonousStack.isEmpty() && seriel[i] <= seriel[monotonousStack.peek()]) {
                int tmp = monotonousStack.pop();
                if (monotonousStack.isEmpty())
                    maxSubscale = Math.max(maxSubscale,i*seriel[tmp]);
                else{
                    int newTop = monotonousStack.peek();
                    int width = i - newTop -1;
                    maxSubscale = Math.max(maxSubscale,seriel[tmp]*width);
                }
            }
            monotonousStack.push(i);
        }
        while (!monotonousStack.isEmpty()) {
            int tmp = monotonousStack.pop();
            int rightBorder = seriel.length;
            int leftBorder = monotonousStack.isEmpty() ? -1 : monotonousStack.peek();
            int width = rightBorder - leftBorder - 1;
            maxSubscale = Math.max(maxSubscale, seriel[tmp] * width);
        }

        return maxSubscale;
    }

    public static void main(String[] args){

        int[][] matrix = {{1, 0, 1, 1},
                {1, 1, 1, 0},
                {1, 1, 1, 0}
        };

        int[] histoArray = geneHistogram(matrix);
        for (int i = 0;i<histoArray.length;i++)
            System.out.println(histoArray[i]);
        System.out.println(getMaxSubSize(histoArray));
    }
}
