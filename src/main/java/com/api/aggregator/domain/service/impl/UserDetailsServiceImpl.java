package com.api.aggregator.domain.service.impl;

import com.api.aggregator.domain.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Class that implements UserDetailsService used in security
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserService userService;

	/**
	 * Get user by username
	 *
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userService.getByUsername(username);
	}
}
