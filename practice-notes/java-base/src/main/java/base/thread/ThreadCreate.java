package base.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.FutureTask;

@Slf4j
public class ThreadCreate {

   /* 线程状态
    New：新创建的线程，尚未执行；
    Runnable：运行中的线程，正在执行run()方法的Java代码；
    Blocked：运行中的线程，因为某些操作被阻塞而挂起；
    Waiting：运行中的线程，因为某些操作在等待中；
    Timed Waiting：运行中的线程，因为执行sleep()方法正在计时等待；
    Terminated：线程已终止，因为run()方法执行完毕。
    */

   // 线程加入 通过对另一个线程对象调用join()方法可以等待其执行结束(相当于插队 必须等插队者结束才能继续之前的操作)
   @Test
   public void testJoin() throws Exception{
       Thread t = new Thread(() -> log.info("hello"));
       log.info("start");
       t.start();
       t.join();
       log.info("end");
   }

    @Test
    public void testThread() throws Exception{
        Thread threadMain = new Thread();
        log.info("main start:{}", threadMain.getName());

//        Thread thread = new Thread(() -> log.info("Runnable:{}", "Runnable run"));
//        thread.start();
//        log.info("Runnable:{}", "Runnable");


        Thread thread1 = new Thread(new Student());
        thread1.start();   // 启动新线程

        Thread thread = new Thread(new Person());
        thread.setDaemon(true);
        thread.start();
//        Person person = new Person();
//        person.start();



        Runnable runnableThread = () -> log.info("new Runnable0():{}", "thread==");
        runnableThread.run();

        Thread newRunnableThread = new Thread(() -> log.info("new Thread1:{}", "threadddddd000"), "threadA");
        newRunnableThread.start();

        final String[] result = {"41543614365"};
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("hahah  进来了！！！！");
            result[0] = "are you speak";
        }, result[0]);
        futureTask.run();
        log.info("get:{}", futureTask.get());
        log.info("result:{}", Arrays.toString(result));

        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.info("Callable==");
            return 101;
        });
        task.run();
        log.info("callable-task:{}", task.get());

        log.info("main end:{}", threadMain.getName());
    }



    static class Person extends Thread{
        @Override
        public void run() {
            log.info("extends Thread");
            super.run();
        }
    }

    static class Student implements Runnable{
        @Override
        public void run() {
            for(int i = 0 ; i<10; i++){
                log.info("implements Runnable:{}", i);
                try {
                    log.info("Runnable:{}", i);
                    Thread.sleep(1000);
                    log.info("Runnable===:{}", i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


