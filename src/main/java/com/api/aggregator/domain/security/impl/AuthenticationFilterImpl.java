package com.api.aggregator.domain.security.impl;

import com.api.aggregator.domain.exception.ExceptionResponse;
import com.api.aggregator.domain.model.Login;
import com.api.aggregator.domain.model.user.User;
import com.api.aggregator.domain.security.IAuthenticationFilter;
import com.api.aggregator.domain.service.ITokenService;
import com.api.aggregator.domain.service.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Responsible to care and guarantee that user is logged or if user can realize log in.
 */
@Component
public class AuthenticationFilterImpl extends OncePerRequestFilter implements IAuthenticationFilter {

	@Autowired
	private AuthenticationManager authManager;

	private ITokenService tokenService;
	private IUserService userService;

	public AuthenticationFilterImpl(ITokenService tokenService, IUserService userService) {
        this.tokenService = tokenService;
		this.userService = userService;
	}

	/**
	 * Check if user is logged.
	 * Verify if has token and valid token.
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = tokenService.retrieve(request);

		if (!Strings.isEmpty(token) && tokenService.isValid(token)) {
			authenticateUser(token);
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * Check the token and if the data are ok.
	 * @param token
	 */
	private void authenticateUser(String token) {
		Long userId = tokenService.getUserId(token);
		User user = userService.getById(userId);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * Call the internal class AuthManager to check if the login exist on database.
	 * This use the bean PasswordEncoder configured to check if the passwords match.
	 * @param authenticationLogin
	 * @return
	 */
	private Authentication authenticateUser(UsernamePasswordAuthenticationToken authenticationLogin) {
		return authManager.authenticate(authenticationLogin);
	}

	private UsernamePasswordAuthenticationToken authenticationToken(Login login) {
		return new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
	}

	/**
	 * Get the username and password, realize login and call method to generate token.
	 * @param login
	 * @return
	 */
	@Override
	public String authenticateLogin(Login login) {
		UsernamePasswordAuthenticationToken authenticationLogin = authenticationToken(login);

		try {
			Authentication authentication = authenticateUser(authenticationLogin);
			return tokenService.createJwt(authentication);
		}catch (Exception e) {
			throw new ExceptionResponse("Invalid user.", HttpStatus.FORBIDDEN);
		}
	}
}
