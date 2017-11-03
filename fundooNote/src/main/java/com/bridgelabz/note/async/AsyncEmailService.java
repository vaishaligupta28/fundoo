package com.bridgelabz.note.async;

import com.bridgelabz.note.services.EmailService;

public class AsyncEmailService implements Runnable {

	private EmailService emailService;
	
	private String emailId;
	private String toEmail;
	private String fromEmail;
	private String subject;
	private String text;

	public AsyncEmailService(String toEmail, String fromEmail, String subject, String text, EmailService emailService) {
		this.toEmail=toEmail;
		this.fromEmail=fromEmail;
		this.subject=subject;
		this.text=text;
		this.emailService = emailService;
	}

	public void run() {
		System.out.println("EmailService running....");
		emailService.sendMailAfterReg(toEmail, fromEmail, subject, text);
	}
}
