package HeapProblem;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author:毛翔宇
 * @Date 2019-05-27 15:44
 */
public class SlidingMedianCodeReview {
    public double[] slidingMedian(int[] nums, int k) {
        List<Integer> orderWindow = new LinkedList<>();
        double[] result = new double[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            int pos = rank(orderWindow, nums[i]);
            orderWindow.add(pos, nums[i]);
        }
        result[0] = ((double) orderWindow.get((k - 1) / 2) + orderWindow.get(k / 2)) / 2;
        for (int end = k; end < nums.length; end++) {
            int delIndex = rank(orderWindow, nums[end - k]);
            orderWindow.remove(delIndex);
            int addIndex = rank(orderWindow, nums[end]);
            orderWindow.add(addIndex, nums[end]);
            result[end - k + 1] = ((double) orderWindow.get((k - 1) / 2) + orderWindow.get(k / 2)) / 2;
        }
        return result;
    }

    public int rank(List<Integer> nums, int key) {
        int low = 0, high = nums.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comp = nums.get(mid).compareTo(key);
            if (comp < 0)
                low = mid + 1;
            else if (comp > 0)
                high = mid - 1;
            else return mid;
        }
        return low;
    }
}
