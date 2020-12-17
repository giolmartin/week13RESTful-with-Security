package com.meritamerica.week11.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.meritamerica.week11.models.AccountHoldersContactDetails;
import com.meritamerica.week11.repos.AccountHoldersContactDetailsRepository;

public class AccountHoldersContactDetailsService {

	@Autowired
	 private AccountHoldersContactDetailsRepository repository;
	
	public AccountHoldersContactDetails addContactDetails(AccountHoldersContactDetails contact) {
		return repository.save(contact);
	}
}
