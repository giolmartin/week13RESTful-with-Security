package com.meritamerica.week11.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity
@DiscriminatorValue("CD_ACCOUNT")
public class CDAccount extends BankAccount{


	
	private int term;
	
	@ManyToOne
	@JoinColumn(name = "offerings_id")
	private CDOffering cdOffering;

	public CDOffering getCdOffering() {
		return cdOffering;
	}

	public void setCdOffering(CDOffering cdOffering) {
		this.cdOffering = cdOffering;
	}
	
	public CDAccount() {
		super();
	}
	
	public CDAccount( double balance, double interestRate, int term) {
		super(balance, interestRate);
			this.term = term;
		
	}
	
	
	
	
	public int getTerm() {
		
		return this.term;
	}
	
	public void setTerm(int term) {
		this.term = term;
	}
	
	
}
