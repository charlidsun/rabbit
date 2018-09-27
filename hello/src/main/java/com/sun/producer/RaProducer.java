package com.sun.producer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

//提供者
public class RaProducer {

	//交换机名字
	private static final String EXCHANGE_NAME = "exchange_name";
	//
	private static final String ROUTING_KEY = "routing_name";
	//队列名字
	private static final String QUEUE_NAME = "queue_name";
	private static final String ADDRESS = "192.168.99.100";
	private static final Integer PORT = 5672;

	public static void main(String[] args) throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
		ConnectionFactory factory = new ConnectionFactory();
		//第一种连接方式
		//factory.setHost(ADDRESS);
		//factory.setPort(PORT);
		//factory.setUsername("sjg");
		//factory.setPassword("123456");
		//第二种连接方式
		factory.setUri("amqp://sjg:123456@192.168.99.100:5672");
		
		// 创建连接
		Connection connection = factory.newConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		// 创建一个 type="direct" 、持久化的、非自动删除的交换器
		channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
		// 创建一个持久化、非排他的、非自动删除的队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		// 将交换器与队列通过路由键绑定
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		// 发送一条持久化的消息: hello world !
		String message = "Hello World ssssssssss!";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		// 关闭资源
		channel.close();
		connection.close();
	}
}
