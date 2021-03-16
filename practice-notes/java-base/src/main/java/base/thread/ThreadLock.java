package base.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ThreadLock {

    static final Object LOCK_A = new Object();

    static final Object LOCK_B = new Object();

    public static void main(String[] args) throws Exception {
        new Thread1().start();
        new Thread2().start();
    }

    static void sleep1s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Thread1 extends Thread {
        public void run() {
            System.out.println("Thread-1: try get lock A...");
            synchronized (ThreadLock.LOCK_A) {
                System.out.println("Thread-1: lock A got.");
                ThreadLock.sleep1s();
                System.out.println("Thread-1: try get lock B...");
                synchronized (ThreadLock.LOCK_B) {
                    System.out.println("Thread-1: lock B got.");
                    ThreadLock.sleep1s();
                }
                System.out.println("Thread-1: lock B released.");
            }
            System.out.println("Thread-1: lock A released.");
        }
    }

    static class Thread2 extends Thread {
        public void run() {
            System.out.println("Thread-2: try get lock A...");
            synchronized (ThreadLock.LOCK_A) {
                System.out.println("Thread-2: lock A got.");
                ThreadLock.sleep1s();
                System.out.println("Thread-2: try get lock B...");
                synchronized (ThreadLock.LOCK_B) {
                    System.out.println("Thread-2: lock B got.");
                    ThreadLock.sleep1s();
                }
                System.out.println("Thread-2: lock B released.");
            }
            System.out.println("Thread-2: lock A released.");
        }

    }


    /**
     * 观察synchronized修饰的add()方法，一旦线程执行到add()方法内部，说明它已经获取了当前实例的this锁。如果传入的n < 0，将在add()方法内部调用dec()方法。由于dec()方法也需要获取this锁，现在问题来了：
     * 对同一个线程，能否在获取到锁以后继续获取同一个锁？
     * 答案是肯定的。JVM允许同一个线程重复获取同一个锁，这种能被同一个线程反复获取的锁，就叫做可重入锁。
     * 由于Java的线程锁是可重入锁，所以，获取锁的时候，不但要判断是否是第一次获取，还要记录这是第几次获取。每获取一次锁，记录+1，每退出synchronized块，记录-1，减到0的时候，才会真正释放锁。
     */
    class Counter {
        private int count = 0;

        public synchronized void add(int n) {
            if (n < 0) {
                desc(-n);
            } else {
                count += n;
            }
        }

        public synchronized void desc(int n) {
            count += n;
        }
    }

    @Test
    public void test() throws Exception{
        Counter counter = new Counter();
        Thread thread3 = new Thread(() -> {
            counter.add(-10);
            log.info("count3:{}", counter.count);
        });

        Thread thread4 = new Thread(() -> {
            counter.add(10);
            log.info("count4:{}", counter.count);
        });

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();
    }
}





