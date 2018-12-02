import edu.princeton.cs.algs4.StdOut;

class Solution {
    public int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (true) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) low = mid;
            else high = mid;
            if (low == high) return low;
        }

    }

    public static void main(String[] args) {
    }


}
