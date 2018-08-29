package com.example.demo;

import java.util.Currency;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * Runnable random generator for publishing to Kafka
 * @author William Nortman
 *
 */
public class KafkaLoadRunner implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(KafkaLoadRunner.class.getName());

	public volatile Boolean keepRunning = true;
	
	public void stop() {
		keepRunning = false;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		KafkaATMTransactionProducer atmProducer = new KafkaATMTransactionProducer();
		Random rnd = new Random();
				
		while (this.keepRunning) {
			// Random Data Fill
			Integer atmID = rnd.nextInt(6000);
			Integer accountID = rnd.nextInt(600000);
			String cardNumber = "4365-" + (rnd.nextInt(8000)+1000) + "-" + (rnd.nextInt(8000)+1000) + "-" +(rnd.nextInt(8000)+1000);
			Float amount = rnd.nextInt(20)*20.00f;
			byte[] imageTmp = new byte[1024];
			rnd.nextBytes(imageTmp);

			// Generate and Set Withdrawal
			
			AtmWithDrawTransaction atwt = new AtmWithDrawTransaction(atmID, accountID, cardNumber, amount, imageTmp);
			atmProducer.send(atwt);
			log.info("Sent ATM Transaction");
			try {
				Thread.sleep(rnd.nextInt(1000*15));
			} catch (InterruptedException ex) {
				keepRunning = false; // Exit on Interrupt
			}
			
		}
		
	}

}
