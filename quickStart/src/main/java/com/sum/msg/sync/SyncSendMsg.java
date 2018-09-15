package com.sum.msg.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.sum.msg.util.ConstData;

/**
 * 同步发送
 * @author Administrator
 *
 */
public class SyncSendMsg {
		
	public SyncSendMsg() {
	}
	
	public void run(String addr) throws Exception{
		
		//定义生产者
		DefaultMQProducer producer = new DefaultMQProducer("sun");
		//设置地址
		producer.setNamesrvAddr(addr);
		//开始
		producer.start();
		
		//发送
		for (int i=0;i<5;i++) {
			//发送消息
			Message message = new Message("TopicTest", "STAR", "i am producer".getBytes(RemotingHelper.DEFAULT_CHARSET));
			SendResult sendResult = producer.send(message);
			System.err.printf("%s%n",sendResult);
		}
		
		producer.shutdown();
	}
	
	public static void main(String[] args) throws Exception {
		new SyncSendMsg().run(ConstData.getServerAddress());
	}

}
