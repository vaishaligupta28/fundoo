package com.bridgelabz.note.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bridgelabz.note.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenizerService {

	private static Logger logger = Logger.getLogger(TokenizerService.class);

	private static final String CLIENT_SECRET = "secret";

	public static String generateToken(User user) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		/*
		 * byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("secret");
		 * System.out.println(apiKeySecretBytes); Key signingKey = new
		 * SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		 * 
		 */
		long ttlMillis = TimeUnit.MINUTES.toMillis(10000); 
		long nowMillis = System.currentTimeMillis();
		
		long expireMillis = nowMillis+ttlMillis;
	    Date expireDate = new Date(expireMillis);
	    
		logger.info("Generating the token");
		System.out.println("Inside generate token"+user.getUserId());
		String jws = Jwts.builder().setIssuer("bridgelabz").setSubject("Its a good day")
				.claim("name", user.getFullName()).claim("id", user.getUserId())
				.setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
				.setExpiration(expireDate)
				.signWith(signatureAlgorithm, "secret").compact();
		System.out.println("Token:  " + jws);
		return jws;
	}

	public static Claims verifyToken(String token) {

		// String token = TokenizerService.generateToken(clientSecret);
		Claims claims = Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();

		System.out.println("ID: " + claims.getId());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issued at: " + claims.getIssuedAt());
		System.out.println(claims.get("id"));
		return claims;
	}
}
