package com.example.demo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaLoadGenerator implements DisposableBean, Runnable {
	private static final Logger log = LoggerFactory.getLogger(KafkaLoadGenerator.class.getName());
	
	private Thread thread;
	ArrayList<KafkaLoadRunner> runners = new ArrayList<>();
	private Boolean keepRunning = true;
	
	KafkaLoadGenerator() {
		log.info("Starting Kafka Load Generator");
		this.thread = new Thread(this);
		this.thread.start();

	}
	
	
	@Override
	public void run() { 
		int generatorCount = 5;
				
		for(int i=0;i<generatorCount;i++) {
			log.info("Starting Thread "+i++);
			KafkaLoadRunner klr = new KafkaLoadRunner();
			Thread runnerThread = new Thread(klr);
			runnerThread.start();
			runners.add(klr);
		}
		int countDown = 10;
		while(keepRunning) {
			try {
				thread.sleep(5000);
				countDown--;
				if (countDown < 0) keepRunning = false;
			} catch (InterruptedException e) {
				keepRunning = false;
			}
			
		}
	}
	
	@Override
	public void destroy() {
		int cnt=1;
		for (KafkaLoadRunner kafkaLoadRunner : runners) {
			log.info("Stopping Thread "+cnt);
			kafkaLoadRunner.stop();
		}
	}
}
