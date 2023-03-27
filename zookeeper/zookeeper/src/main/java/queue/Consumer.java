package queue;


import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            String take = queue.take(); //取元素
            System.out.println("我拿到了: " + take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
