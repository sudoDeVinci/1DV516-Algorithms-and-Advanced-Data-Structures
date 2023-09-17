package src.ksum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoSum {
    public List<int[]> twoSum (Integer[] nums, Integer target, Integer start) {
        List<int[]> result = new ArrayList<>();
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = start; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                int[] pair = {nums[seen.get(complement)], nums[i]};
                result.add(pair);
            }
            seen.put(nums[i], i);
        }

        return result;
    }
}