package base.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ThreadReadWriteLock {

    /**
     * 只允许一个线程写入（其他线程既不能写入也不能读取）；
     * 没有写入时，多个线程允许同时读（提高性能）。
     * 可以提高读取效率：适合读多写少的场景
     * 有线程正在读，写线程需要等待读线程释放锁后才能获取写锁，即读的过程中不允许写 这是一种悲观的读锁
     * */

    private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private final Lock rlock = rwlock.readLock();
    private final Lock wlock = rwlock.writeLock();
    private int[] counts = new int[10];

    public void inc(int index) {
        wlock.lock(); // 加写锁
        try {
            counts[index] += 1;
        } finally {
            wlock.unlock(); // 释放写锁
        }
    }

    public int[] get() {
        rlock.lock(); // 加读锁
        try {
            return Arrays.copyOf(counts, counts.length);
        } finally {
            rlock.unlock(); // 释放读锁
        }
    }

}
