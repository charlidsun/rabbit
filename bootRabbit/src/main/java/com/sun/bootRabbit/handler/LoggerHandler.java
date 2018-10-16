package com.sun.bootRabbit.handler;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.sun.bootRabbit.domain.Product;

@Component
public class LoggerHandler {
		
	Logger logger = LoggerFactory.getLogger(LoggerHandler.class);

	@RabbitListener(queues = "${basic.info.mq.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMessage(List<Product> products){
        try {
        	logger.info("接收到实体消息： {} ",Arrays.toString(products.toArray()));
        }catch (Exception e){
        	logger.error("监听消费消息 发生异常： ",e.fillInStackTrace());
        }
    }
}
