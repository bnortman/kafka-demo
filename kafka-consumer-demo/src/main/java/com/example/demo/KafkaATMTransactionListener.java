package com.example.demo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.google.gson.Gson;

public class KafkaATMTransactionListener {

	Queue<String> bufferedMessage = new LinkedList<String>();
	Gson gson = new Gson();
	private Consumer<String, String> listener;

	KafkaATMTransactionListener() {
        Map<String, Object> props = new HashMap<>();
        props.put(
          ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          "192.168.1.115:2181");
        props.put(
          ConsumerConfig.GROUP_ID_CONFIG, 
          "1");
        props.put(
          ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        props.put(
          ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
          StringDeserializer.class);
        DefaultKafkaConsumerFactory<String,String> factory = new DefaultKafkaConsumerFactory<>(props);
        this.listener = factory.createConsumer();
	}
	
	@KafkaListener(topics = "withdrawls", groupId = "1")
	public void listen(String message) {
		int cnt = 0;
		while(bufferedMessage.size() > 1000) {
			try {
				cnt++;
				Thread.sleep(1000);
				if (cnt>1000) throw new TimeoutException("Queue Backing Up");
			} catch (InterruptedException e) {
			}
		}
		bufferedMessage.add(message);
	}

	public AtmWithDrawTransaction read() {
		if (bufferedMessage.size() == 0) return null;
		String message = bufferedMessage.poll();
		AtmWithDrawTransaction awdt = gson.fromJson(message, AtmWithDrawTransaction.class);
		return awdt;
		
	}

}
