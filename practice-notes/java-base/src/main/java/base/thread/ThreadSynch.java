package base.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadSynch {

    /**
     * volatile只保证时效性：读主内存到本地副本；操作本地副本; 回写主内存。
     * 同步的本质就是给指定对象加锁(保证原子性)，加锁后才能继续执行后续代码；
     * 注意加锁对象必须是同一个实例；
     * 对JVM定义的单个原子操作不需要同步
     * */
    public static void main(String[] args) throws Exception{
        Thread addThread = new Add();
        Thread descThread = new Desc();
        addThread.start();
        descThread.start();
        descThread.join();
        addThread.join();

        log.info("总数:{}", Count.num);
    }


    static class Count {
        private static volatile int num = 0;
        private static final Object obj = new Object();
    }

    static class Add extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
//                synchronized (Count.obj) {
                    Count.num += 1;
//                    log.info("add:{}", Count.num);
//                }
            }
        }
    }

    static class Desc extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
//                synchronized (Count.obj) {
                    Count.num -= 1;
//                    log.info("desc:{}", Count.num);
//                }
            }
        }
    }

}
