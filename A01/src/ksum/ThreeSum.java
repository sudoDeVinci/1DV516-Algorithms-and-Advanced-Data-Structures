package src.ksum;

import java.util.ArrayList;
import java.util.List;


/**
 * brute-force implementation of 3-sum.
 */
public class ThreeSum {
    TwoSum two;
    public ThreeSum () {
        two = new TwoSum();
    }
    
    public List<int[]> threeSum(Integer [] nums, Integer target) {
        List<int[]> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                for (int k = 0; k < nums.length; k++) {
                    if (i==j || i == k || j== k) {
                        continue;
                    }

                    if (nums[i] + nums [j] + nums [k] == 0) {
                        result.add(new int[] {nums[i], nums[j], nums[k]});
                    }
                }   
            }
        }
        return result;
    }
}
