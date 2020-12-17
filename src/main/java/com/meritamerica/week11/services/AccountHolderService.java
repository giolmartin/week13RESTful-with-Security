package com.meritamerica.week11.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.week11.exceptions.ExceedsCombinedLimitException;
import com.meritamerica.week11.exceptions.NoSuchResourceFoundException;
import com.meritamerica.week11.models.AccountHolder;
import com.meritamerica.week11.models.AccountHoldersContactDetails;
import com.meritamerica.week11.models.CDAccount;
import com.meritamerica.week11.models.CDOffering;
import com.meritamerica.week11.models.CheckingAccount;
import com.meritamerica.week11.models.SavingsAccount;
import com.meritamerica.week11.repos.AccountHolderRepository;
import com.meritamerica.week11.repos.CDAccountRepository;
import com.meritamerica.week11.repos.CDOfferingRepository;
import com.meritamerica.week11.repos.CheckingAccountRepository;
import com.meritamerica.week11.repos.SavingsAccountRepository;
@Service
public class AccountHolderService {
	
	protected static final double MAX_DEPOSIT_AMOUNT = 250000;
	
	@Autowired
	AccountHolderRepository repository;
	
	@Autowired
	CheckingAccountRepository checkingRepository;
	
	@Autowired
	CDAccountRepository cdAccountRepository;
	
	@Autowired 
	CDOfferingRepository cdOfferingRepository;
	
	@Autowired
	SavingsAccountRepository savingsRepository;
	
	public AccountHolder postAccountHolder(AccountHolder accountHolder) {
		AccountHoldersContactDetails contact = accountHolder.getContact();
		contact.setAccountHolder(accountHolder);
		return repository.save(accountHolder);
	}
	
	public List<AccountHolder> getAccountHolders(){
		return repository.findAll();
	}
	
	public AccountHolder findById(int id) throws NoSuchResourceFoundException {
		return repository.findById(id).orElseThrow(() -> new NoSuchResourceFoundException("Invalid Account Holder Id"));
	}
	
	public CDOffering findCDOfferingById(int id) throws NoSuchResourceFoundException {
		return cdOfferingRepository.findById(id).orElseThrow(() -> new NoSuchResourceFoundException("Invalid CDOffering Id"));
	}
	
	public CheckingAccount postCheckingAccount(int id, CheckingAccount checkingAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		checkingAccount.setAccountHolder(ah);
		Double balance = checkingAccount.getBalance();
		if (ah.combinedBalance() + balance > MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {	
			checkingAccount.setAccountHolder(ah);
		return checkingRepository.save(checkingAccount);
		}
	}
	
	public SavingsAccount postSavingsAccount(int id, SavingsAccount savingsAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		Double balance = savingsAccount.getBalance();	
		if (ah.combinedBalance() + balance > MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {
			savingsAccount.setAccountHolder(ah);
			return savingsRepository.save(savingsAccount);
		}
	}

	public CDAccount postCDAccount(int id, @Valid CDAccount cdAccount) throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder ah = findById(id);
		CDOffering cdO = findCDOfferingById(cdAccount.getCdOffering().getId());
		Double balance = cdAccount.getBalance();
		if (ah.combinedBalance() + balance > MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}else {
			cdAccount.setAccountHolder(ah);
			cdAccount.setCdOffering(cdO);
			cdAccount.setTerm(cdO.getTerm());
			cdAccount.setInterestRate(cdO.getInterestRate());
			return cdAccountRepository.save(cdAccount);
		}
		
	}
	
	public CDOffering postCDOffering(@Valid CDOffering cdOffering) {
		return cdOfferingRepository.save(cdOffering);
	}

	public List<CheckingAccount> getCheckingAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getCheckingAccounts();
	}

	public List<SavingsAccount> getSavingsAccount(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getSavingsAccounts();
	}

	public List<CDAccount> getCDAccounts(int id) throws NoSuchResourceFoundException {
		AccountHolder ah = findById(id);
		return ah.getCdAccounts();
	}
	
	public List<CDOffering> getCDOfferings(){
		return cdOfferingRepository.findAll();
	}
	
	
	public String deleteAccountHolder(int id) {
		String msg = "Account " + id+ " Deleted";
		repository.deleteById(id);
		return msg;
	}
	
	/*
	public Boolean exceedsOrNot(double combinedBalance, double balance) throws ExceedsCombinedLimitException {
		if (combinedBalance + balance > MAX_DEPOSIT_AMOUNT) {
			throw new ExceedsCombinedLimitException("Deposit exceeds the " + MAX_DEPOSIT_AMOUNT + " total.");
		}
		else {
			return true;
		}
	}
	*/
	
}
