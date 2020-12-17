package com.meritamerica.week13.security.models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
@Entity
@Table(name="CD_OFFERINGS")
public class CDOffering {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cd_offerings_generator")
	private int id;
	
	@DecimalMin(value = "1.0", inclusive = true, message = "Term must be greater than 1")
	@DecimalMax(value = "15.0", inclusive = false, message = "Term  must be lower than 15")
	private int term;
	
	@DecimalMin(value = "0.0", inclusive = false, message = "Interest must be greater than 0")
	@DecimalMax(value = "1.0", inclusive = false, message = "Interest rate must be lower than 1")
	private double interestRate;
	
	public  CDOffering() {
	}
	
	public CDOffering(int term, double interestRate) {
		this.term = term;
		this.interestRate = interestRate;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
