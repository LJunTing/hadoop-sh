package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProductTool {
    String user = ActiveMQConnection.DEFAULT_USER;
    String password = ActiveMQConnection.DEFAULT_PASSWORD;
    String url = "tcp://mini1:61616";
    //订阅主题
    String subject = "mytopic";
    private Connection connection;
    private Session session;
    private Topic topic;
    private MessageProducer producer;

    //初始化
    public void initalize() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connection = connectionFactory.createConnection();
        //创建会话
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //会话再链接创建主题
        topic = session.createTopic(subject);
        //创建生产者
        producer = session.createProducer(topic);
        //发送模式  非持久化的模式
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
    }

    //发送消息
    public void sendMessage(String message) throws JMSException {
        initalize();
        //会话创建消息类型
        TextMessage textMessage = session.createTextMessage(message);
        connection.start();
        System.out.println("sending message:  " + textMessage);
        //生产者发送消息
        producer.send(textMessage);
        System.out.println("消息发送完成");

    }

    //关闭链接
    public void close() throws JMSException {
        if (producer != null) {
            producer.close();
        }
        if (session != null) {
            session.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

}
