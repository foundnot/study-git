package base.thread;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class ThreadCompletableFuture {

    /**
     * CompletableFuture的优点是：
     *  异步任务结束时，会自动回调某个对象的方法；
     *  异步任务出错时，会自动回调某个对象的方法；
     *  主线程设置好回调后，不再关心异步任务的执行
     *
     * CompletableFuture可以指定异步处理流程：
     *  thenAccept()处理正常结果；
     *  exceptional()处理异常结果；
     *  thenApplyAsync()用于串行化另一个CompletableFuture；
     *  anyOf()和allOf()用于并行化多个CompletableFuture。
     * */

    @Test
    public void test1() throws Exception{
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(ThreadCompletableFuture::fetchPrice);
        // 如果执行成功:
        cf.thenAccept((result) -> System.out.println("price: " + result));
        // 如果执行异常:
        cf.exceptionally((e) -> {
            log.info("exception:{}", e.getMessage());
            return null;
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }

    /**
     * anyOf()可以实现“任意个CompletableFuture只要一个成功”，就返回
     * allOf()可以实现“所有CompletableFuture都必须成功”
     * */
    public static void main(String[] args) throws Exception {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> queryCode("中国石油", "https://finance.sina.com.cn/code/"));
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> queryCode("163", "https://money.163.com/code/"));

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> fetchPrice((String) code, "https://finance.sina.com.cn/price/"));

        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> fetchPrice((String) code, "https://money.163.com/price/"));

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> System.out.println("price: " + result));
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }

}
