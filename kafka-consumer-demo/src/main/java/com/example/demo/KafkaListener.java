package com.example.demo;

import java.util.Map;

public class KafkaListener implements Runnable {

	
	KafkaListener() {

	}
	
	public void run() {
		KafkaATMTransactionListener atmListener = new KafkaATMTransactionListener();

		while(keepRunning) {
			
			AtmWithDrawTransaction atmWithDrawal = atmListener.read();
		}
		
	}

	public volatile Boolean keepRunning = true;
	
	public void stop() {
		keepRunning = false;
	}
}
