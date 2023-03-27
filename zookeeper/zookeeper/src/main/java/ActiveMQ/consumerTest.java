package ActiveMQ;

import javax.jms.JMSException;

public class consumerTest implements Runnable {


    public static void main(String[] args) {
        Thread thread = new Thread(new consumerTest());
        thread.setDaemon(false);
        thread.start();


    }

    public void run() {
        try {
            ConsumerTool consumerTool = new ConsumerTool();
            consumerTool.readMessage();
            //一直链接着就阻塞线程  一直读取消息
            while (consumerTool.isconnection) {
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
