package queue;

import java.util.concurrent.LinkedBlockingQueue;

public class TestBlockingQueue {

    public static void main(String[] args) {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < 3; i++) {
            new Thread(producer, "pro: " + (i + 1)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(consumer, "Con: " + (i + 1)).start();
        }
        new Thread(producer, "pro" + (5)).start();
    }

}
