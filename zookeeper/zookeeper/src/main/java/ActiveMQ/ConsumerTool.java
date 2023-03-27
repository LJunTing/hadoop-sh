package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerTool implements MessageListener, ExceptionListener {

    //链接的准备参数
    String user = ActiveMQConnection.DEFAULT_USER;
    String password = ActiveMQConnection.DEFAULT_PASSWORD;
    String url = "tcp://mini1:61616";
    //订阅主题
    String subject = "mytopic";
    private Connection connection;
    private Session session;
    private Topic topic;
    private MessageConsumer consumer;

    //初始化
    public void initalize() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connection = connectionFactory.createConnection();
        //创建会话
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //会话再链接创建主题
        topic = session.createTopic(subject);
        //创建消费者
        consumer = session.createConsumer(topic);
    }


    public boolean isconnection;


    public void readMessage() throws JMSException {
        initalize();
        connection.start();
        consumer.setMessageListener(this);
        connection.setExceptionListener(this);
        isconnection = true;
        System.out.println("consumer: ---start listening...");
    }

    public void close() throws JMSException {
        System.out.println("关闭链接");
        if (consumer != null) {
            consumer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                text = textMessage.getText();
                System.out.println("读取文本消息为:  " + text);
            } else {
                System.out.println("读取消息类型[非文本]: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void onException(JMSException e) {
        isconnection = false;
    }
}
