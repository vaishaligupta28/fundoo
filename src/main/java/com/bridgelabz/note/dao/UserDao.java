package com.bridgelabz.note.dao;

import org.springframework.stereotype.Component;

import com.bridgelabz.note.model.Login;
import com.bridgelabz.note.model.User;

@Component
public interface UserDao {

	void register(User user);

	User loginUser(Login login);

	int changePass(User user);

	User getUserByEmailID(String emailId);
}
