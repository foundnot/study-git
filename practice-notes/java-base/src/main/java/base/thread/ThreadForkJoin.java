package base.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


@Slf4j
public class ThreadForkJoin {

    /**
     * Fork/Join是一种基于“分治”的算法：通过分解任务，并行执行，最后合并结果得到最终结果。
     * ForkJoinPool线程池可以把一个大任务分拆成小任务并行执行，任务类必须继承自RecursiveTask或RecursiveAction
     * 使用Fork/Join模式可以进行并行计算以提高效率
     * */

    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = new StopWatch();
        // 创建2000个随机数组成的数组:
        long[] array = new long[20];
        long expectedSum = 0;
        stopWatch.start("单线程统计");
        for (int i = 0; i < array.length; i++) {
            array[i] = random();
            expectedSum += array[i];
        }
        stopWatch.stop();

        // fork/join:
        stopWatch.start("分线程统计");
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        Long result = ForkJoinPool.commonPool().invoke(task);
        stopWatch.stop();
        log.info("统计结果 expectedSum:{}===result:{}", expectedSum, result);
        log.info("耗时统计：{}", stopWatch.prettyPrint());

        Arrays.parallelSort(array);
        log.info("parallelSort:{}", array);
    }

    static Random random = new Random(0);

    static long random() {
        return random.nextInt(10000);
    }
}


class SumTask extends RecursiveTask<Long> {
    static final int THRESHOLD = 500;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += this.array[i];
                // 故意放慢计算速度:
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
            }
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }

}
