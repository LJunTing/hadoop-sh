package queue;

import java.util.concurrent.BlockingQueue;

//生产者
public class Producer implements Runnable {
    BlockingQueue<String> queue;

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        System.out.println(" made a product:" + Thread.currentThread().getName());
        String temp = "A product ,生产线程: " + Thread.currentThread().getName();
        try {
            queue.put(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
