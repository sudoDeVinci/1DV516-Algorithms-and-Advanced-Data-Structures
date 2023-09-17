package src.ksum;

import java.util.ArrayList;
import java.util.List;

public class ThreeSumCached extends ThreeSum{
    public ThreeSumCached() {
        super();
    }
    public List<int[]> threeSum(Integer [] nums, Integer target) {
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            List<int[]> twoSumRes = this.two.twoSum(nums, target-current, i+1);

            if(twoSumRes.isEmpty()) continue;

            for (int[] pair : twoSumRes) {
                int[] trip = {current, pair[0], pair[1]};
                result.add(trip);
            }
        }
        return result;
    }
}
