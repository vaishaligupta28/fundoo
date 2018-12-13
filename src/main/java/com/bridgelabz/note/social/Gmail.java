package com.bridgelabz.note.social;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.bridgelabz.note.model.GmailProfile;
import com.bridgelabz.note.model.GmailToken;


public class Gmail {

	private static final String sSCOPE = "profile email";
	private static final String sGMAIL_CLIENT_ID = "1097381024907-lhi6fj5kavmbbij90ipagvvqaepbrvla.apps.googleusercontent.com";
	private static final String sGMAIL_CLIENT_SECRET = "6Ic-XYq4Z_-YcSxy01Iik7do";
	private static final String sGMAIL_REDIRECT_URL = "/postGoogle";

	private static final String sGMAIL_URL = "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&state=%s&response_type=code&scope=%s&approval_prompt=force&access_type=offline";
	private static final String sGMAIL_ACCESS_TOKEN_URL = "https://www.googleapis.com/oauth2/v4/token";
	private static final String sGMAIL_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me";

	private static Logger logger = Logger.getLogger(Gmail.class);

	public static String getGmailUrl(String apiRedirectUrl, String stateCode) {
		logger.warn("inside get Gmail Url");
		apiRedirectUrl = apiRedirectUrl + sGMAIL_REDIRECT_URL;

		return new String().format(sGMAIL_URL, new String[] { sGMAIL_CLIENT_ID, apiRedirectUrl, stateCode, sSCOPE });
	}

	public static GmailProfile authUser(String authCode, String apiRedirectUrl) {

		String accessToken = getAccessToken(authCode, apiRedirectUrl);
		return getUserProfile(accessToken);
	}

	private static GmailProfile getUserProfile(String accessToken) {

		String accessProfileUrl = sGMAIL_GET_USER_URL;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(accessProfileUrl);
		String headerAuth = "Bearer " + accessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();
		GmailProfile profile = response.readEntity(GmailProfile.class);
		// String profile1 = response.readEntity(String.class);
		// logger.warn(profile1);
		client.close();
		return profile;
	}

	private static String getAccessToken(String authCode, String apiRedirectUrl) {

		apiRedirectUrl = apiRedirectUrl + sGMAIL_REDIRECT_URL;
		String accessTokenUrl = sGMAIL_ACCESS_TOKEN_URL;

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(accessTokenUrl);
		Form form = new Form();
		form.param("client_id", sGMAIL_CLIENT_ID);
		form.param("client_secret", sGMAIL_CLIENT_SECRET);
		form.param("redirect_uri", apiRedirectUrl);
		form.param("code", authCode);
		form.param("grant_type", "authorization_code");

		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		GmailToken token = response.readEntity(GmailToken.class);
		// String gmailToken = response.readEntity(String.class);
		// logger.warn(gmailToken);
		client.close();
		return token.getAccess_token();
	}

}
