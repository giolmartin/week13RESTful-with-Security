
package com.meritamerica.week11.controller;

import java.util.*;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.week11.models.*;
import com.meritamerica.week11.services.AccountHolderService;
import com.meritamerica.week11.exceptions.*;



@RestController
public class MeritBankController {

	Logger log = LoggerFactory.getLogger(this.getClass()); 	
	
	@Autowired
	private AccountHolderService service;
	
	@GetMapping(value = "/account-holders")
	public List<AccountHolder> getAccountHolder() {
		log.info("Returned account holders");
		return service.getAccountHolders();
	}
	
	@PostMapping(value = "/account-holder")
	@ResponseStatus(HttpStatus.CREATED)
	public  AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		//try catch here i think
		log.info("User has added account");
		return service.postAccountHolder(accountHolder);
		//	MeritBank.addAccountHolder(accountHolder);
	}
	
	
	@GetMapping(value = "/account-holders/{accountID}")
	public AccountHolder getAccountByID(@PathVariable("accountID") int id) throws NoSuchResourceFoundException {
		log.info("Returned Account Holder");
		return service.findById(id);
	}
	
	
	@PostMapping(value = "/account-holders/{accountID}/checking-account")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount postCheckingAccount(@PathVariable("accountID") int id, @RequestBody @Valid CheckingAccount checkingAccount) 
												throws NoSuchResourceFoundException, ExceedsCombinedLimitException{
		log.info("Checking Account created and Added");
		return service.postCheckingAccount(id, checkingAccount);
	}
	
	@GetMapping(value = "/account-holders/{accountID}/checking-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccount(@PathVariable("accountID") int id) 
												throws NoSuchResourceFoundException{
		log.info("Checking Account fetched");
		return service.getCheckingAccount(id);
	}
	
	@PostMapping(value = "/account-holders/{accountID}/savings-account")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount postSavingsAccount(@PathVariable("accountID") int id, @RequestBody @Valid SavingsAccount savingsAccount) 
											throws NoSuchResourceFoundException, ExceedsCombinedLimitException{
		log.info("Savings Account created and Added");
		return service.postSavingsAccount(id, savingsAccount);
	}
	
	@GetMapping(value = "/account-holders/{accountID}/savings-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getSavingsAccount(@PathVariable("accountID") int id) 
												throws NoSuchResourceFoundException{
		log.info("Savings Account fetched");
		return service.getSavingsAccount(id);
	}
	
	@PostMapping(value = "/account-holders/{accountID}/cd-account")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount postCDAccount(@PathVariable("accountID") int id, @RequestBody @Valid CDAccount cdAccount) 
									throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		cdAccount.getCdOffering().getId();
		return service.postCDAccount(id, cdAccount);
	}
	
	@GetMapping(value = "/account-holders/{accountID}/cd-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CDAccount> getCDAccount(@PathVariable("accountID") int id) throws NoSuchResourceFoundException{
		return service.getCDAccounts(id);
	}
	
	@PostMapping(value = "/cd-offering")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering postCDOffering(@RequestBody @Valid CDOffering cdOffering) throws NoSuchResourceFoundException {
		return service.postCDOffering(cdOffering);
	}
	
	@GetMapping(value = "/cd-offerings")
	@ResponseStatus(HttpStatus.OK)
	public List<CDOffering> getCDOfferings(){
		log.info("CD Offerings returned");
		return service.getCDOfferings();
	}
	
	@DeleteMapping(value = "/account-holders/{accountID}/delete-account")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteAccountHolder(@PathVariable("accountID") int id) {
		log.info("Account Deleted");
		return service.deleteAccountHolder(id);
	}
}
