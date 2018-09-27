package com.sun.consumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

//消费者
public class RaConsumer {

	private static final String QUEUE_NAME = "queue_name";
	private static final String ADDRESS = "192.168.99.100";
	private static final Integer PORT = 5672;

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

		Address[] address = new Address[] { new Address(ADDRESS, PORT) };

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("sjg");
		factory.setPassword("123456");

		Connection connection = factory.newConnection(address);
		final Channel channel = connection.createChannel();
		//设置每次从队列获取消息的数量
		channel.basicQos(64);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				System.out.println(" recv message: " + new String(body));
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, consumer);
		// 等待回调函数执行完毕之后 关闭资源
		TimeUnit.SECONDS.sleep(5);
		channel.close();
		connection.close();
	}

}
