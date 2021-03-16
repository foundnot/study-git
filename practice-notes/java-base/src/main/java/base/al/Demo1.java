package base.al;



import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 常见简单算法
 * */
@Slf4j
public class Demo1 {

    public static void main(String[] args) {
        log.info("twoSum:{}", twoSum(new int[]{1,2,3,4,5,6}, 3));
    }


    // 给定一个数组，给定一个数字。返回数组中可以相加得到指定数字的两个索引
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            // 数组中元素值为key  索引为values
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

}
