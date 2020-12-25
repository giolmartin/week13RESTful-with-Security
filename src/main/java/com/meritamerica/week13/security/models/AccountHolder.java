package com.meritamerica.week13.security.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT_HOLDERS")
public class AccountHolder {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_holder_generator")
	@Column(name = "account_holder_id")
	private int id;

	@NotBlank(message = "Name may not be empty")
	// @Size(min = 2, max = 32, message = "Name must be between 2-32 characters
	// long")
	private String firstName;

	private String middleName;

	@NotBlank(message = "Need last name")
	private String lastName;

	@NotBlank(message = "Need to specify ssn")
	// @Size(min = 11, max = 11, message = "Not a valid SSN")
	private String ssn;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	private Users user;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountHolder")
	private AccountHoldersContactDetails contact;

	@OneToMany(targetEntity = CheckingAccount.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountHolder")
	private List<CheckingAccount> checkingAccounts;

	@OneToMany(targetEntity = SavingsAccount.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountHolder")
	private List<SavingsAccount> savingsAccounts;

	@OneToMany(targetEntity = CDAccount.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountHolder")
	private List<CDAccount> cdAccounts;

	@JsonIgnore
	private double totalBalance;

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public double getTotalBalance() {
		return combinedBalance();
	}

	public List<CheckingAccount> getCheckingAccounts() {
		return checkingAccounts;
	}

	public void setCheckingAccounts(List<CheckingAccount> checkingAccounts) {
		this.checkingAccounts = checkingAccounts;
	}

	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}

	public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
		this.savingsAccounts = savingsAccounts;
	}

	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}

	public void setCdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}

	public AccountHolder() {
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.ssn = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public AccountHoldersContactDetails getContact() {
		return contact;
	}

	public void setContact(AccountHoldersContactDetails contact) {
		this.contact = contact;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public double combinedBalance() {

		for (CheckingAccount i : checkingAccounts) {
			totalBalance = i.getBalance();
		}
		for (SavingsAccount i : savingsAccounts) {
			totalBalance = i.getBalance();
		}
		for (CDAccount i : cdAccounts) {
			totalBalance = i.getBalance();
		}

		return totalBalance;
	}
}
