package cn.edu.scnu;


import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TopicMode {
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
        channel.exchangeDeclare("topic01", "topic");

        channel.queueDeclare("guangdong", false, false, false, null);
        channel.queueDeclare("foshan", false, false, false, null);
        channel.queueDeclare("nanhai", false, false, false, null);

        String msg = "20202005105";
        channel.queueBind("guangdong", "topic01", "guangdong.#");
        channel.queueBind("foshan", "topic01", "guangdong.foshan.#");
        channel.queueBind("nanhai", "topic01", "guangdong.foshan.nanhai.*");

//        channel.basicPublish("direct01", "west", null, "daliyouyong".getBytes());
        channel.basicPublish("topic01", "guangdong.foshan", null, msg.getBytes());

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

