package com.meritamerica.week11.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends BankAccount {

	private static final double INTEREST_RATE = 0.001 ;
	public SavingsAccount() {
		super(INTEREST_RATE);
	}
	
	public SavingsAccount(double balance) {
		super(balance,INTEREST_RATE);
		}

	
	
}
