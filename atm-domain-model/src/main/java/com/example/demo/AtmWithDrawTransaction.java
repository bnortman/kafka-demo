package com.example.demo;

import java.util.Currency;

public class AtmWithDrawTransaction {
	public Integer atmID;
	public Integer accountID;
	public String cardNumber;
	public Float amount;
	public byte[] transactionImage;

	public AtmWithDrawTransaction(Integer atmID, Integer accountID, String cardNumber, Float amount,
			byte[] transactionImage) {
		super();
		this.atmID = atmID;
		this.accountID = accountID;
		this.cardNumber = cardNumber;
		this.amount = amount;
		this.transactionImage = transactionImage;
	}

	public Integer getAtmID() {
		return atmID;
	}

	public void setAtmID(Integer atmID) {
		this.atmID = atmID;
	}

	public Integer getAccountID() {
		return accountID;
	}

	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public byte[] getTransactionImage() {
		return transactionImage;
	}

	public void setTransactionImage(byte[] transactionImage) {
		this.transactionImage = transactionImage;
	}
	
	
	

}
