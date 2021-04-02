package QueryProblem;

import java.util.LinkedList;

/**
 * @Author:毛翔宇
 * @Date 2019-03-11 15:29
 */
public class MaxNumSlidingWindow {

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0)
            return null;
        LinkedList<Integer> window = new LinkedList<>();
        int index = 0;
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (!window.isEmpty() && nums[window.peekLast()] <= nums[i])
                window.pollLast();
            window.add(i);
            if (window.peek() == i - k)
                window.pollFirst();
            if (i >= k - 1)
                res[index++] = nums[window.peek()];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {4, 3, 5, 4, 3, 3, 6, 7};
        int[] nums2 = {1, 3, 1, 2, 0, 5};
        int[] res = maxSlidingWindow(nums2, 3);
        for (int i = 0; i < res.length; i++)
            System.out.printf("%d%s", res[i], " ");
    }
}
