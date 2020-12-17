package com.meritamerica.week11.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT_HOLDERS_CONTACT")
public class AccountHoldersContactDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_generator")
	@Column(name ="contact_id")
	private int id;
	
	private int phoneNo;
	private String email;
	
	@OneToOne
	@JoinColumn(name = "account_holder_id", nullable=false)
	@JsonIgnore
	private AccountHolder accountHolder;

	
	public AccountHoldersContactDetails() {
		
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
