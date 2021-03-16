package base.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 对目标线程调用interrupt()方法可以请求中断一个线程，目标线程通过检测isInterrupted()标志获取自身是否已中断。
 *      如果目标线程处于等待状态，该线程会捕获到InterruptedException；
 * 目标线程检测到isInterrupted()为true或者捕获了InterruptedException都应该立刻结束自身线程；
 *
 * 通过标志位判断需要正确使用volatile关键字；
 * volatile关键字解决了共享变量在线程间的可见性问题。
 *
 * */
@Slf4j
public class ThreadStop {

   /**
    * volatile关键字的目的是告诉虚拟机： volatile保证时效性不是原子性
    *    每次访问变量时，总是获取主内存的最新值；
    *    每次修改变量后，立刻回写到主内存
    **/

    /**
     * running标志位来标识线程是否应该继续运行 在外部线程中，
     *  通过把HelloThread.running置为false，就可以让线程结束
     * */
    @Test
    public void test1() throws Exception{
        WorldThread t = new WorldThread();
        t.start();
        t.running = false; // 标志位置为false
        Thread.sleep(1);
    }


    /**
     * interrupt()  中断线程  仅仅向t线程发出了“中断请求”，至于t线程是否能立刻响应，要看具体代码
     *
     * main线程通过调用t.interrupt()从而通知t线程中断，而此时t线程正位于hello.join()的等待中，
     * 此方法会立刻结束等待并抛出InterruptedException。由于我们在t线程中捕获了InterruptedException，
     * 因此，就可以准备结束该线程。在t线程结束前，对hello线程也进行了interrupt()调用通知其中断。如果去掉这一行代码，
     * 可以发现hello线程仍然会继续运行，且JVM不会退出。
     * */
    public static void main(String[] args) throws Exception{
        Thread t = new MyThread();
        t.setName("t-MyThread");
        t.start();

        log.info("thread:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
        log.info("t-thread-state:{}", t.getState().name());
        log.info("sleep==before");
        Thread.sleep(1000);
        log.info("sleep==after");
        log.info("t-thread-before-state:{}", t.getState().name());
        t.interrupt(); // 终止t线程 并不是线程立即停止 而是改变t线程的状态
        log.info("t-thread-after-state:{}", t.getState().name());
        t.join();      // 等待t线程结束 main线程再运行
        log.info("t-state2:{}", t.getState().name());
        log.info("thread-state:{}", Thread.currentThread().getState());
        log.info("end");
    }

    static class MyThread extends Thread {
        public void run() {
            log.info("MyThread0:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
            Thread hello = new HelloThread();
            hello.setName("hello-thread");
            hello.start(); // 启动hello线程
            try {
                log.info("join==before==");

                hello.join(); // t线程等待hello线程结束
                log.info("join==");
                log.info("MyThread1:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
            } catch (InterruptedException e) {
                log.info("MyThread2:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
                log.info("interrupted!");
            }
            log.info("MyThread3:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
            hello.interrupt();
            log.info("MyThread4:{}====thread-state:{}", hello.getName(), hello.getState());
        }
    }

    static class  HelloThread extends Thread {
        public void run() {
            log.info("HelloThread1:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
            int n = 0;
            log.info("HelloThread1==thread-state:{}", Thread.currentThread().isInterrupted());
            while (!isInterrupted()) {
                n++;
                log.info(n + " hello!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.info("interrupted22!");
                    log.info("HelloThread2:{}====thread-state:{}", Thread.currentThread().getName(), Thread.currentThread().getState());
                    break;
                }
            }
        }
    }

    static class WorldThread extends Thread {
        public volatile boolean running = true;
        public void run() {
            int n = 0;
            while (running) {
                n ++;
                System.out.println(n + " hello!");
            }
            System.out.println("end!");
        }
    }
}
