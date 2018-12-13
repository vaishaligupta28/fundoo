package com.bridgelabz.note.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.note.dao.UserDaoImpl;
import com.bridgelabz.note.model.Login;
import com.bridgelabz.note.model.User;


@Service
public class UserService {

	@Autowired
	private UserDaoImpl daoImpl;

	@Transactional
	public void register(User user) {
		System.out.println("user service- register()");
		daoImpl.register(user);
	}

	public User loginUser(Login login) {
		// TODO Auto-generated method stub
		System.out.println("user service- validateUser()");
		System.out.println("Email: " + login.getEmail());
		System.out.println("Password: " + login.getPassword());
		return daoImpl.loginUser(login);
	}

	public int changePass(User user) {
		System.out.println("user service - changePass()");
		return daoImpl.changePass(user);
	}

	public User getUserByEmailID(String emailId) {
		System.out.println("user service - getUserByEmailID()");
		return daoImpl.getUserByEmailID(emailId);
	}
}
