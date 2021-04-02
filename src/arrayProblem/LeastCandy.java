package arrayProblem;

/**
 * @Author:毛翔宇
 * @Date 2019/1/13 10:47 AM
 * leetcode ID 135 Candy
 */
public class LeastCandy {
    public int leastCandyDistribution(int[] ratings){
        if (ratings.length == 1)
            return 1;
        int left_slope=1,right_slope; //左、右坡度
        int index = nextLeftSlopeStart(ratings,0);
        int candy = calcuDown(0,index++);
        while(index != ratings.length){
            if (ratings[index-1] < ratings[index]){
                candy += ++left_slope;
                index ++;
            }
            else if (ratings[index-1] > ratings[index]){
                int next = nextLeftSlopeStart(ratings,index-1);
                int down = calcuDown(index-1,next++);
                right_slope = next - index +1;
                candy += down+(right_slope >= left_slope?-left_slope:-right_slope);
                left_slope = 1;
                index = next;
            }
            else{
                candy ++;
                left_slope =1;
                index++;
            }
        }
        return candy;
    }

    public int nextLeftSlopeStart(int[] arr,int start){
        for (int i = start;i!=arr.length-1;i++){
            if (arr[i] <= arr[i+1])
                return i;
        }
        return arr.length-1;
    }
    public int calcuDown(int left,int right){
        int n = right - left +1;
        return n*(n+1)/2;
    }

    public int advLeastCandyDistribution(int[] ratings){
        if (ratings.length == 1)
            return 1;
        int left_slope=1; //左坡度
        int index = nextLeftSlopeStart2(ratings,0);
        int[] down = advCalcuDown(ratings,0,index++);
        int candy = down[0];
        int same = 1;
        while(index != ratings.length){
            if (ratings[index-1] < ratings[index]){
                candy += ++left_slope;
                same = 1;
                index ++;
            }
            else if (ratings[index-1] > ratings[index]){
                int next = nextLeftSlopeStart2(ratings,index-1);
                down = advCalcuDown(ratings,index-1,next++);
                if (down[1] <= left_slope)
                    candy += down[0] - down[1];
                else
                    candy += (-left_slope*same + down[0]-down[1] + down[1]*same);
                left_slope = 1;
                same = 1;
                index = next;
            }
            else{
                candy += left_slope;
                same++;
                index++;
            }
        }
        return candy;
    }
    public int nextLeftSlopeStart2(int[] arr,int start){
        for (int i = start;i!=arr.length-1;i++){
            if (arr[i] < arr[i+1])
                return i;
        }
        return arr.length-1;
    }
    public int[] advCalcuDown(int[] arr,int left,int right){
        int base = 1,candy=1;
        for (int i = right-1;i>=left;i--){
            if (arr[i] == arr[i+1])
                candy += base;
            else
                candy += ++base;
        }
        return new int[]{candy,base};
    }

    public static void main(String[] args) {
        LeastCandy lc = new LeastCandy();
        int[] test1 = { 3, 0, 5, 5, 4, 4, 0 };
        System.out.println(lc.advLeastCandyDistribution(test1));

        int[] test2 = { 0,1,2,5,3,2,7 };
        System.out.println(lc.leastCandyDistribution(test2));
    }
}
