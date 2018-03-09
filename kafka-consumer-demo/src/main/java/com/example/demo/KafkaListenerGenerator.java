package com.example.demo;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerGenerator implements DisposableBean, Runnable {
	private static final Logger log = LoggerFactory.getLogger(KafkaListenerGenerator.class.getName());

	private Thread thread;
	ArrayList<KafkaListener> listeners = new ArrayList<>();
	private Boolean keepRunning = true;
	
	KafkaListenerGenerator() {
		log.info("Starting Kafka Listener");
		this.thread = new Thread(this);
		this.thread.start();

	}
	
	@Override
	public void run() {
		int listenerCount = 5;
		
		for(int i=0;i<listenerCount;i++) {
			log.info("Starting Thread "+i++);
			KafkaListener lr = new KafkaListener();
			Thread runnerThread = new Thread(lr);
			runnerThread.start();
			listeners.add(lr);
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
			
		}	}

	@Override
	public void destroy() throws Exception {
		int cnt=1;
		for (KafkaListener listener : listeners) {
			log.info("Stopping Thread "+cnt);
			listener.stop();
		}
		
	}
	

}
