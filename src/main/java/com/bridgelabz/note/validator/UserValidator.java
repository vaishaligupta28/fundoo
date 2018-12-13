package com.bridgelabz.note.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.bridgelabz.note.model.Login;
import com.bridgelabz.note.model.User;

public class UserValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = "^.*(?=.{8,15})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
	private static final String FULLNAME_PATTERN = "^([A-Za-z]+[,.]?[ ]?|[A-Za-z]+['-]?)+$";
	private static final String MOBILE_NUM_PATTERN = "^[789]\\d{9}$";

	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	private boolean patternValidator(String regex, String inputString) {
		return Pattern.matches(regex, inputString);
	}

	private boolean isEmptyOrWhiteSpace(String inputString) {
		if (inputString == null || inputString.trim().length() == 0)
			return true;

		return false;
	}

	public void validate(Object target, Errors errors) {

		User user = null;
		Login login = null;

		// validating during registration
		if (target instanceof User) {
			user = (User) target;

			// validating password
			if (isEmptyOrWhiteSpace(user.getPassword()))
				errors.rejectValue("password", "error.register.password", "Password is required");
			else {
				if (user.getPassword().length() < 8 || user.getPassword().length() > 15)
					errors.rejectValue("password", "error.register.length",
							"Password must contain minimum 8 and maximum 15 characters");
				else {
					if (!patternValidator(PASSWORD_PATTERN, user.getPassword()))
						errors.rejectValue("password", "error.register.patternPwd",
								"Password should contain atleast one digit,one lowecase charactere, one uppercase character and one special symbol");
				}
			}
			// validating fullname
			if (isEmptyOrWhiteSpace(user.getFullName()))
				errors.rejectValue("fullname", "error.register.fullname", "Name is required");
			else {
				if (!patternValidator(FULLNAME_PATTERN, user.getFullName()))
					errors.rejectValue("fullname", "error.register.patternFullname", "Invalid name");
			}

			// validating email
			if (isEmptyOrWhiteSpace(user.getEmail()))
				errors.rejectValue("email", "error.register.email", "Email is required");
			else {
				if (!patternValidator(EMAIL_PATTERN, user.getEmail()))
					errors.rejectValue("email", "error.register.patternEmail", "Invalid email address");
			}

			// validating mobile number
			if (isEmptyOrWhiteSpace(user.getMobileNumber()))
				errors.rejectValue("mob", "error.register.mob", "Mobile Number is required");
			else {
				if (!patternValidator(MOBILE_NUM_PATTERN, user.getMobileNumber()))
					errors.rejectValue("mob", "error.register.patternMob", "Invalid mobile number");
			}
		}

		// validating using login
		else {
			login = (Login) target;

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.login.email",
					"Email cannot be left empty");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.login.password",
					"Password cannot be left empty");
			/*
			 * ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
			 * "error.login.password", "Password cannot be left empty");
			 */

			if (!isEmptyOrWhiteSpace(login.getPassword()) && !isEmptyOrWhiteSpace(login.getConfirmPassword())) {

				if (login.getPassword().length() < 8 || login.getPassword().length() > 15)
					errors.rejectValue("password", "error.reset.length",
							"Password must contain minimum 8 and maximum 15 characters");
				else {
					System.out.println(login.getPassword());
					System.out.println(patternValidator(PASSWORD_PATTERN, login.getPassword()));
					if (!patternValidator(PASSWORD_PATTERN, login.getPassword())) {
						System.out.println("-----");
						errors.rejectValue("password", "error.reset.patternPwd",
								"Password should contain atleast one digit,one lowecase charactere, one uppercase character and one special symbol");

					} else {
						System.out.println("2222");
						if (!(login.getPassword().equalsIgnoreCase(login.getConfirmPassword()))) {
							System.out.println("3333");
							errors.rejectValue("confirmPassword", "error.reset.match", "Passwords do not match");
						}
					}
				}
			}
		}

	}

}
