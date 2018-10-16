package com.sun.bootRabbit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sun.bootRabbit.domain.Product;
import com.sun.bootRabbit.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value("${basic.info.mq.exchange.name}")
	public String basicInfoMqExchangeName;
	
	@Value("${basic.info.mq.routing.key.name}")
	public String basicInfoMqRoutingKeyName;
	
	@GetMapping("/list")
	public List<Product> listProduct(){
		
		List<Product> list = new ArrayList<>();
		list = productService.listProduct();
		
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setExchange(basicInfoMqExchangeName);
        rabbitTemplate.setRoutingKey(basicInfoMqRoutingKeyName);

        //Message message=MessageBuilder.withBody(objectMapper.writeValueAsBytes(userLog)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        //message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON); //发送消息写法二
        rabbitTemplate.convertAndSend(list);
		return list;
	}
}
