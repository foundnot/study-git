package base.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock可以替代synchronized进行同步；
 * ReentrantLock获取锁更安全；
 * 必须先获取到锁，再进入try {...}代码块，最后使用finally保证释放锁；
 * 可以使用tryLock()尝试获取锁。
 */
@Slf4j
public class ThreadReentrantLock {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final int num = 50;
    private static int count = 0;


    public static void main(String[] args) throws Exception {
        Thread addThread = new Add();
        Thread descThread = new Desc();
        addThread.start();
        descThread.start();
        descThread.join();
        addThread.join();

        log.info("总数:{}", count);
    }


    static class Add extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                lock.lock();
                try {
                    count++;
                    log.info("add:{}", count);
                } catch (Exception ignored) {

                } finally {
                    lock.unlock();
                }
            }
        }
    }

    static class Desc extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                try {
                    if (lock.tryLock(1, TimeUnit.SECONDS)) {
                        count--;
                        log.info("desc:{}", count);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    /**
     * 使用Condition对象来实现wait和notify的功能
     * 使用Condition时，引用的Condition对象必须从Lock实例的newCondition()返回，这样才能获得一个绑定了Lock实例的Condition实例。
     * Condition提供的await()、signal()、signalAll()原理和synchronized锁对象的wait()、notify()、notifyAll()是一致的，并且其行为也是一样的
     */
    private final Condition condition = lock.newCondition();
    private Queue<String> queue = new LinkedList<>();

    public void addTask(String s) {
        lock.lock();
        try {
            queue.add(s);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getTask() throws Exception {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                condition.await();
            }
            return queue.remove();
        } finally {
            lock.unlock();
        }
    }
}
