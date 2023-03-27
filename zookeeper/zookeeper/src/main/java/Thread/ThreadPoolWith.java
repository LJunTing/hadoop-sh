package Thread;

import java.util.concurrent.*;

public class ThreadPoolWith {

    public static void main(String[] args) {

        test1();
        try {
            test2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 主线程无法获得任务线程结果
     */
    private static void test1() {
        //创建一个线程池
        ExecutorService pool = Executors.newCachedThreadPool();


        for (int i = 0; i < 5; i++) {
            pool.execute(new Runnable() {
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }

    /**
     * 主线程可以获得线程 返回值  需要等到线程执行完毕
     */
    //4个线程  10个任务
    private static void test2() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 9; i++) {
            Future<String> submit = pool.submit(new Callable<String>() {
                public String call() throws Exception {
                    return "--" + Thread.currentThread().getName();
                }
            });
            //从Future 中get结果,这个方法是会被阻塞的,一定要等到线程任务结束
            System.out.println(submit.get());
        }
        //关闭线程池
        pool.shutdown();
    }


}
