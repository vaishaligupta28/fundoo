package com.bridgelabz.note.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgelabz.note.json.LoginResponse;
import com.bridgelabz.note.json.Response;
import com.bridgelabz.note.model.Login;
import com.bridgelabz.note.model.User;
import com.bridgelabz.note.json.SignUpErrorResponse;
import com.bridgelabz.note.services.EmailService;
import com.bridgelabz.note.services.RedisService;
import com.bridgelabz.note.services.TokenizerService;
import com.bridgelabz.note.services.UserService;
import com.bridgelabz.note.validator.UserValidator;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private EmailService emailService;

	private int OTP;
	private String emailID;
	private Logger logger = Logger.getLogger(LoginController.class);

	private String CLIENT_ID = "";

	@RequestMapping("/test")
	public void testMethod() {
		System.out.println("test");
		logger.info("test method");
	}

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody ResponseEntity<Response> loginUser(@RequestBody Login login, BindingResult bindResult,
			HttpServletRequest req) {

		logger.info("entered login user");
		Response resp;
		userValidator.validate(login, bindResult);

		if (bindResult.hasErrors()) {
			logger.info("login validation errors");
			logger.info(bindResult.getFieldErrors().toString());
			resp = new Response();
			resp.setStatus(-1);
			resp.setMsg("some validation errors occured");
			return new ResponseEntity<Response>(resp, HttpStatus.CONFLICT);
		}

		try {
			User user = userService.loginUser(login);
			System.out.println(user);
			if (null != user) {
				LoginResponse loginResp;
				// creating the clientId respective to the user
				logger.info("Login successfull!!");

				CLIENT_ID = "user##".concat(String.valueOf(user.getUserId()));
				System.out.println(CLIENT_ID);
				String token = TokenizerService.generateToken(user);
				RedisService.setToken(token, CLIENT_ID);

				loginResp = new LoginResponse();
				loginResp.setStatus(1);
				loginResp.setMsg("User exists!!Login successfull");
				loginResp.setToken(token);

				/*
				 * HttpSession session = req.getSession(true); session.setAttribute("user",
				 * user);
				 */

				HttpHeaders respHeaders = new HttpHeaders();
				respHeaders.add("token", token);
				return new ResponseEntity<Response>(loginResp, respHeaders, HttpStatus.OK);
			} else {
				logger.info("User does not exists.Please register!!");
				return new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.info("Server error occured");
			resp = new SignUpErrorResponse();
			resp.setStatus(-1);
			resp.setMsg("Internal server occured");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "logout")
	public @ResponseBody ResponseEntity<Response> logoutUser(HttpSession session) {

		Response resp;
		try {
			logger.info("Getting the session parameters");
			User user = (User) session.getAttribute("user");
			logger.info(user.getUserId());
			try {
				session.invalidate();
				//deleting token from redis
				CLIENT_ID = "user##".concat(String.valueOf(user.getUserId()));
				RedisService.deleteToken(CLIENT_ID);
				
				resp = new Response();
				resp.setStatus(1);
				resp.setMsg("Session invalidated successfully");
				return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
			} catch (Exception e) {
				resp = new Response();
				resp.setStatus(-1);
				resp.setMsg("Session ended unfortunately!!Please login to continue.");
				logger.info("Session ended unfortunately!!Please login to continue");
				return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			resp = new Response();
			resp.setStatus(-1);
			resp.setMsg("Session ended unfortunately!!Please login to continue.");
			;
			logger.info("Session ended unfortunately!!Please login to continue");
			return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
		}
	}

	@ResponseBody
	@RequestMapping(value = "forgotPassword")
	public ResponseEntity<Response> forgotPassword() {
		logger.info("Login Controller- forgot pass");
		Response resp = new Response();
		resp.setStatus(1);
		resp.setMsg("Page loaded successfully");
		return new ResponseEntity<Response>(HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "sendOTP", method = RequestMethod.POST)
	public ResponseEntity<Response> sendOTP(@RequestBody User user) {

		logger.info("Login Controller- reset password");

		emailID = user.getEmail();
		// check if the email id is valid and registered with us.
		try {
			if (userService.getUserByEmailID(emailID) == null) {
				logger.info("User not found error");
				return new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
			} else {
				try {
					OTP = emailService.sendMailForReset(emailID);
					logger.info("OTP:    ");
					logger.info(OTP);
					return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
				} catch (Exception e) {
					logger.info("Error sending email to and retreiving OTP");
					return new ResponseEntity<Response>(HttpStatus.REQUEST_TIMEOUT);
				}
			}
		} catch (Exception e) {
			logger.info("Internal server error");
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "reset")
	public ResponseEntity<Response> resetPassword(@RequestParam int OTP, int formOTP) {

		Response resp;
		logger.info("Login Controller- reset password after clicking the link");
		logger.info(OTP);
		logger.info(formOTP);
		if (formOTP == OTP) {
			resp = new Response();
			resp.setStatus(1);
			resp.setMsg("OTP validated");
			logger.info("OTP validated");
			return new ResponseEntity<Response>(HttpStatus.OK);
		} else {
			resp = new Response();
			resp.setStatus(-1);
			resp.setMsg("Incorrect OTP");
			logger.info("Incorrect OTP");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "resendOTP")
	public ResponseEntity<Response> sendOTP() {
		logger.info("Login Controller- reset password");
		try {
			OTP = emailService.sendMailForReset(emailID);
			logger.info("OTP:    ");
			logger.info(OTP);
			return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			logger.info("Error sending email and retreiving OTP");
			return new ResponseEntity<Response>(HttpStatus.REQUEST_TIMEOUT);
		}
	}

	@ResponseBody
	@RequestMapping(value = "changePass", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Response> changePassword(@RequestBody Login login, BindingResult bindResult) {
		System.out.println("Login Controller- changePassword() method");

		Response resp;
		User user;
		// check for valid password, and both passwords matching
		userValidator.validate(login, bindResult);

		if (bindResult.hasErrors()) {
			logger.info("login validation errors");
			logger.info(bindResult.getFieldErrors().toString());
			resp = new Response();
			resp.setStatus(-1);
			resp.setMsg("some validation errors occured");
			return new ResponseEntity<Response>(HttpStatus.CONFLICT);
		}
		try {
			user = userService.getUserByEmailID(login.getEmail());
			logger.info(user);
			if (user != null) {
				logger.info("user found");
				user.setPassword(login.getPassword());
			} else {
				return new ResponseEntity<Response>(HttpStatus.NOT_FOUND);
			}
			try {
				logger.info("New password");
				logger.info(user.getPassword());
				int rowsAffected = userService.changePass(user);
				if (rowsAffected > 0) {
					resp = new Response();
					resp.setStatus(1);
					resp.setMsg("Data changed successfully");
					logger.info("data changed sucessfully");
					return new ResponseEntity<Response>(HttpStatus.OK);
				} else {
					return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				logger.info("server error");
				return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			logger.info("server error");
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
