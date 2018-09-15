package com.sum.msg.async;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.sum.msg.util.ConstData;

/**
 *  异步发送
 * @author Administrator
 *
 */
public class AsyncSendMsg {

	private AsyncSendMsg() {
	}
	
	public void run(String addr) throws Exception{
		
		
		DefaultMQProducer producer = new DefaultMQProducer("sunas");
		producer.setNamesrvAddr(addr);
		producer.start();
		producer.setRetryTimesWhenSendAsyncFailed(0);
		
		for (int i=0;i<5;i++) {
			final int index = i;
			//创建消息
			//Message message = new Message("TopicTest", "star", "Hello MQ".getBytes(RemotingHelper.DEFAULT_CHARSET));
			Message message = new Message("TopicTest1", "STAR1", "OrderID188","i am producer".getBytes(RemotingHelper.DEFAULT_CHARSET));

			//调用发送的回调
			producer.send(message, new SendCallback() {
				
				public void onSuccess(SendResult sendResult) {
					 System.out.printf("%-10d OK %s %n", index,
	                            sendResult.getMsgId());
				}
				
				public void onException(Throwable e) {
					 System.err.printf("%-10d Exception %s %n", index, e);
                     e.printStackTrace();
				}
			});
		}
		
		producer.shutdown();
		
	}
	
	
	public static void main(String[] args) throws Exception {
		new AsyncSendMsg().run(ConstData.getServerAddress());
	}
}
