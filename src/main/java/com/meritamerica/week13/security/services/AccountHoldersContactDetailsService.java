package com.meritamerica.week13.security.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.meritamerica.week13.security.models.AccountHoldersContactDetails;
import com.meritamerica.week13.security.repos.AccountHoldersContactDetailsRepository;

public class AccountHoldersContactDetailsService {

	@Autowired
	 private AccountHoldersContactDetailsRepository repository;
	
	public AccountHoldersContactDetails addContactDetails(AccountHoldersContactDetails contact) {
		return repository.save(contact);
	}
}
