package com.sun.bootRabbit.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Administrator
 *
 */
@Configuration
public class RabbitConfig {

	//获取基础信息
	@Value("${basic.info.mq.exchange.name}")
	public String basicInfoMqExchangeName;
	
	@Value("${basic.info.mq.routing.key.name}")
	public String basicInfoMqRoutingKeyName;
	
	@Value("${basic.info.mq.queue.name}")
	public String basicInfoMqQueueName;
	
	 @Autowired
	 private CachingConnectionFactory connectionFactory;
	 
	 /**
     * 配置消息交换机
     * 针对消费者配置
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
	@Bean
	public DirectExchange basicDirectExchange() {
		//?????
		return new DirectExchange(basicInfoMqExchangeName, true, false);
	}
	
	/**
	 * 配置队列
	 * @return
	 */
	@Bean(name="basicQueue")
	public Queue basicQueue() {
		return new Queue(basicInfoMqQueueName, true);//支持持久化
	}
	
	/**
	 * 队列  绑定 交换机  用 routingkey
	 * @return
	 */
	@Bean
	public Binding basicBinding() {
		return BindingBuilder.bind(basicQueue()).to(basicDirectExchange()).with(basicInfoMqRoutingKeyName);
	}
	
	/**
     * 单一消费者	
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
