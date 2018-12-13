package com.bridgelabz.note.model;

public class User {

	private int userId;
	private String fullName;
	private String mobileNumber;
	private String email;
	private String password;
	private Image image;


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", fullName=" + fullName + ", mobileNumber=" + mobileNumber + ", email="
				+ email + ", password=" + password + "]";
	}
	
}
