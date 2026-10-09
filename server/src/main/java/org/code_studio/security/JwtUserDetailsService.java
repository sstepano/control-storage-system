package org.code_studio.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.code_studio.database.ApplicationUser;
import org.code_studio.controller.ApplicationUserController;


/**
 * Klasa koja mora da implementira UserDetailsService interfejs, poziva je SpringBoot da dobije username/password validnog usera.
 * Mi pozivamo bazu sa findByUsername da pronadjemo usera sa prosledjenom kombinacijom user/pass
 * TODO: Verovatno moze pametnije da se uradi, kmoristeci SB mehanizme. Istraziti.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	ApplicationUserController appUserController;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<ApplicationUser> appUser = appUserController.findByUsername(username);
		
		if (appUser != null && appUser.size() > 0) {
			return new User(appUser.get(0).getUsername(), new BCryptPasswordEncoder().encode(appUser.get(0).getPassword()), new ArrayList<>());
		} else {
			System.out.println("errthrow->loadUserByUsername:UsernameNotFoundException");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}