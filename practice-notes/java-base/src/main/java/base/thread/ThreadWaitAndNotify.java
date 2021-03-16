package base.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
public class ThreadWaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        TaskQueue queue = new TaskQueue();
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final int ii = i;
            Thread executeTask = new Thread(() -> {
                // 执行task:
                while (true) {
                    try {
                        String s = queue.getTask();
                        log.info("executeTask{}：{}", ii, s);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
            executeTask.start();
            ts.add(executeTask);
        }

        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                log.info("addTask{}:{}", i, s);
                queue.addTask(s);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });

        addThread.start();
        addThread.join();
        Thread.sleep(100);
        for (Thread t : ts) {
            t.interrupt();
        }
    }


    static class TaskQueue {
        Queue<String> queue = new LinkedList<>();
        public synchronized void addTask(String s) {
            this.queue.add(s);
            this.notifyAll();
            log.info("notifyAll");
        }

        public synchronized String getTask() throws InterruptedException {
            //if只在第一次获得锁之后判断一次，while在任意一次获得锁之后都会判断
            //就是用if判断的话，唤醒后线程会从wait之后的代码开始运行，但是不会重新判断if条件，
            // 直接继续运行if代码块之后的代码，而如果使用while的话，也会从wait之后的代码运行，
            // 但是唤醒后会重新判断循环条件，如果不成立再执行while代码块之后的代码块，成立的话继续wait
            while (queue.isEmpty()) {
                log.info("wait");
                this.wait();
                log.info("wait- after");
            }
            log.info("restart");
            return queue.remove();
        }
    }

}
