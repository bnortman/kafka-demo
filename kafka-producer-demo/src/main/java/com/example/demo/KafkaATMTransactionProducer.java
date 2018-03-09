package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.*;
import com.example.demo.AtmWithDrawTransaction;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaATMTransactionProducer {
	private static final Logger log = LoggerFactory.getLogger(KafkaATMTransactionProducer.class.getName());
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	KafkaATMTransactionProducer() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
          ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, 
          "192.168.1.115:2181");
        configProps.put(
          ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        configProps.put(
          ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, 
          StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(configProps);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
	}
	
	
	Gson gson = new Gson();
	
	public void send(AtmWithDrawTransaction atwt) {
		//Get Topic Configuration
		String topic = "withdrawls";
		//Convert to JSON
		
		String jsonATMWithDraw = gson.toJson(atwt);
		
		kafkaTemplate.send(topic,jsonATMWithDraw);
		
		
	}

	
}
