package nl.bos.appworks.utilities;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import jakarta.jms.*;

public class SendRabbitTextMessage {
    public static void main(String[] args) throws JMSException {
        Connection connection;
        Session session;

        RMQConnectionFactory factory = new RMQConnectionFactory();
        factory.setHost("opa.mydomain.com");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        // --- Create JMS connection/session ---
        connection = factory.createConnection("admin", "admin");
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        RMQDestination destination = new RMQDestination("myqueue", true, false);

        // --- Create producer and text message ---
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage("Hello world from the Java sender!");

        // --- Send the message ---
        producer.send(message);
        System.out.println("✅ Message sent to RabbitMQ queue!");

        session.close();
        connection.close();
    }
}