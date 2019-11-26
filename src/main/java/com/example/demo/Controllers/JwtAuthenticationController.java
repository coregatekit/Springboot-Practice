package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Service.JwtUserDetailsService;

import com.example.demo.Config.JwtTokenUtil;
import com.example.demo.Entitys.JwtRequest;
import com.example.demo.Entitys.JwtResponse;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	// @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    // public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, Object> authenticationRequest) throws Exception {
    //     try {
    //         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.get("username").toString(), authenticationRequest.get("password").toString()));
    //     } catch (BadCredentialsException e) {
    //         throw new Exception("Incorrect username or password");
    //     }
    //     final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.get("username").toString());
    //     final String jwt = jwtTokenUtil.generateToken(userDetails);
    //     Map<String, Object> authenticationResponse = new HashMap<>();
    //     authenticationResponse.put("token", jwt);
    //     return ResponseEntity.ok(authenticationResponse);
    // }

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody JwtRequest user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}