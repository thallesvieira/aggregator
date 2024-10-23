package com.api.aggregator.application.web.controller;

import com.api.aggregator.domain.model.Login;
import com.api.aggregator.domain.model.Token;
import com.api.aggregator.domain.security.IAuthenticationFilter;
import com.api.aggregator.domain.validate.IValidateLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller responsible to realize login and sign out
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private IAuthenticationFilter auth;

	@Autowired
	private IValidateLogin validateLogin;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Login login) {
		try {
			validateLogin.validateLogin(login);

            logger.info("User: {} trying  to log in", login.getUsername());
			String jwt = auth.authenticateLogin(login);

			logger.info("User: {} trying successfully logged in", login.getUsername());
			return ResponseEntity.ok(new Token(jwt));
		} catch (Exception ex) {
			logger.error("Error to login for user: {}", login.getUsername());
			throw ex;
		}
	}
}
