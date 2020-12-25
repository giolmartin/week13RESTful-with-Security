
package com.meritamerica.week13.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.week13.security.authentication.AuthenticationRequest;
import com.meritamerica.week13.security.authentication.AuthenticationResponse;
import com.meritamerica.week13.security.exceptions.ExceedsCombinedLimitException;
import com.meritamerica.week13.security.exceptions.NoSuchResourceFoundException;
import com.meritamerica.week13.security.models.AccountHolder;
import com.meritamerica.week13.security.models.CDAccount;
import com.meritamerica.week13.security.models.CDOffering;
import com.meritamerica.week13.security.models.CheckingAccount;
import com.meritamerica.week13.security.models.MyUserDetails;
import com.meritamerica.week13.security.models.SavingsAccount;
import com.meritamerica.week13.security.models.Users;
import com.meritamerica.week13.security.services.AccountHolderService;
import com.meritamerica.week13.security.services.UsersDetailsService;
import com.meritamerica.week13.security.util.JwtUtil;

@RestController
public class MeritBankController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountHolderService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsersDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;

	// <-------------------------------------------------------------------------------ADMINISTRATOR------------------------------------------------------------------------------->

	@GetMapping(value = "/account-holders")
	public List<AccountHolder> getAccountHolder() {
		log.info("Returned account holders");
		return service.getAccountHolders();
	}

	@PostMapping(value = "/account-holder")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		log.info("User has added account");
		return service.postAccountHolder(accountHolder);
	}

	@GetMapping(value = "/account-holders/{accountID}")
	public AccountHolder getAccountByID(@PathVariable("accountID") int id) throws NoSuchResourceFoundException {
		log.info("Returned Account Holder");
		return service.findById(id);
	}

	@PostMapping(value = "/account-holders/{accountID}/checking-account")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount postCheckingAccount(@PathVariable("accountID") int id,
			@RequestBody @Valid CheckingAccount checkingAccount)
			throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		log.info("Checking Account created and Added");
		return service.postCheckingAccount(id, checkingAccount);
	}

	@GetMapping(value = "/account-holders/{accountID}/checking-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccount(@PathVariable("accountID") int id)
			throws NoSuchResourceFoundException {
		log.info("Checking Account fetched");
		return service.getCheckingAccount(id);
	}

	@PostMapping(value = "/account-holders/{accountID}/savings-account")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount postSavingsAccount(@PathVariable("accountID") int id,
			@RequestBody @Valid SavingsAccount savingsAccount)
			throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		log.info("Savings Account created and Added");
		return service.postSavingsAccount(id, savingsAccount);
	}

	@GetMapping(value = "/account-holders/{accountID}/savings-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getSavingsAccount(@PathVariable("accountID") int id)
			throws NoSuchResourceFoundException {
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
	public List<CDAccount> getCDAccount(@PathVariable("accountID") int id) throws NoSuchResourceFoundException {
		return service.getCDAccounts(id);
	}

	@PostMapping(value = "/cd-offering")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering postCDOffering(@RequestBody @Valid CDOffering cdOffering) throws NoSuchResourceFoundException {
		return service.postCDOffering(cdOffering);
	}

	@GetMapping(value = "/cd-offerings")
	@ResponseStatus(HttpStatus.OK)
	public List<CDOffering> getCDOfferings() {
		log.info("CD Offerings returned");
		return service.getCDOfferings();
	}

	@DeleteMapping(value = "/account-holders/{accountID}/delete-account")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteAccountHolder(@PathVariable("accountID") int id) {
		log.info("Account Deleted");
		return service.deleteAccountHolder(id);
	}

	// <-------------------------------------------------------------------------------AUTHENTICATION------------------------------------------------------------------------------->

	@PostMapping(value = "/authenticate")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@PostMapping(value = "authenticate/create-user")
	@ResponseStatus(HttpStatus.CREATED)
	public Users postNewUser(@RequestBody @Valid Users user) {
		return userDetailsService.postNewUser(user);
	}

	// <-------------------------------------------------------------------------------USER-API------------------------------------------------------------------------------->

	@GetMapping(value = "/user")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder getAccount(@RequestHeader("Authorization") String jwt) {
		String username = jwtTokenUtil.extractUserName(jwt.substring(7));
		MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);

		return userDetails.getAccountHolder();
	}

	@PostMapping(value = "/user/checking-account")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount postCheckingAccount(@RequestHeader("Authorization") String jwt,
			@RequestBody CheckingAccount checkingAccount)
			throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.postCheckingAccount(accountHolder.getId(), checkingAccount);

	}

	@PostMapping(value = "/user/savings-account")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount postSavingsAccount(@RequestHeader("Authorization") String jwt,
			@RequestBody SavingsAccount savingsAccount)
			throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.postSavingsAccount(accountHolder.getId(), savingsAccount);

	}

	@PostMapping(value = "/user/cd-account")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount postCDAccount(@RequestHeader("Authorization") String jwt, @RequestBody CDAccount cdAccount)
			throws NoSuchResourceFoundException, ExceedsCombinedLimitException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.postCDAccount(accountHolder.getId(), cdAccount);
	}

	@GetMapping(value = "/user/checking-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CheckingAccount> getCheckingAccounts(@RequestHeader("Authorization") String jwt)
			throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.getCheckingAccount(accountHolder.getId());
	}

	@GetMapping(value = "/user/savings-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<SavingsAccount> getSavingsAccount(@RequestHeader("Authorization") String jwt)
			throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.getSavingsAccount(accountHolder.getId());
	}

	@GetMapping(value = "/user/cd-accounts")
	@ResponseStatus(HttpStatus.OK)
	public List<CDAccount> getCDAccounts(@RequestHeader("Authorization") String jwt)
			throws NoSuchResourceFoundException {
		AccountHolder accountHolder = getAccount(jwt);
		return service.getCDAccounts(accountHolder.getId());
	}
}
