package base.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadConcurrent {

    /**
     * java.util.concurrent.atomic提供的原子操作可以简化多线程编程
     * CAS（Compare and Swap）
     * java.util.concurrent包提供的线程安全的并发集合可以大大简化多线程编程 多线程同时读写并发集合是安全的
     * interface	non-thread-safe	         thread-safe
     * List	        ArrayList	             CopyOnWriteArrayList
     * Map	        HashMap	                 ConcurrentHashMap
     * Set	        HashSet/TreeSet	         CopyOnWriteArraySet
     * Queue	    ArrayDeque/LinkedList	 ArrayBlockingQueue/LinkedBlockingQueue
     * Deque	    ArrayDeque/LinkedList	 LinkedBlockingDeque
     * */


    static int count = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    // 加1操作
    static void add() {
        count++;
        atomicInteger.addAndGet(1);  // 源码中使用CAS（Compare and Swap）
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        // 开5个线程
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                // 每个线程执行1000次add方法
                for (int num = 0; num < 1000; num++) {
                    add();
                }
            });
            threads.add(thread);
            thread.start();
        }
        // 保证上面开启的五个线程执行在main线程之前
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("count-->" + count);
        System.out.println("atomic-->" + atomicInteger.get());
    }

}
