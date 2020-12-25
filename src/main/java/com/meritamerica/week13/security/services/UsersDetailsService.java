package com.meritamerica.week13.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meritamerica.week13.security.models.MyUserDetails;
import com.meritamerica.week13.security.models.Users;
import com.meritamerica.week13.security.repos.UserRepository;

@Service
public class UsersDetailsService implements UserDetailsService {

	@Autowired
	UserRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> user = usersRepository.findByUsername(username);

		user.orElseThrow(() -> new UsernameNotFoundException("Did not find a username " + username));
		return user.map(MyUserDetails::new).get();
	}

	public Optional<Users> loadUserById(int id) {
		return usersRepository.findById(id);
	}

	public Users postNewUser(Users user) {
		return usersRepository.save(user);
	}

}
