package cn.edu.scnu;


import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FanoutMode {
    private Channel channel;

    @Before
    public void initChannel() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("116.205.130.21");
        factory.setPort(5672);
        factory.setUsername("loomt");
        factory.setPassword("123456");
        factory.setVirtualHost("/");
        Connection conn = factory.newConnection();
        channel = conn.createChannel();
    }

    @Test
    public void send() throws Exception {
        channel.exchangeDeclare("fanout01", "fanout");
        channel.queueDeclare("xulaoshi", false, false, false, null);
        channel.queueDeclare("wulaoshi", false, false, false, null);

        channel.queueBind("xulaoshi", "fanout01", "");
        channel.queueBind("wulaoshi", "fanout01", "");

        channel.basicPublish("fanout01", "", null, "dajialaikaihui".getBytes());

    }

    @Test
    public void consumer01() throws Exception {
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("1 get " + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("location", consumer);
    }

    @Test
    public void consumer02() throws Exception {
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("2 get " + message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("location", false, consumer);
    }
}

