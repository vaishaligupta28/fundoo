package com.bridgelabz.note.json;

public class LoginResponse extends Response {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponse [token=" + token + "]";
	}
}
