package com.bridgelabz.note.auth;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.note.services.RedisService;
import com.bridgelabz.note.services.TokenizerService;

import io.jsonwebtoken.Claims;

public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setContentType("application/json");
		// Response resp = (Response) response;
		String authtoken = req.getHeader("token");
		System.out.println("AuthenticationFilter - doFilter()");
		// authtoken = "";

		if (authtoken != null && authtoken.trim().length() != 0) {
			System.out.println("Token received from header: " + authtoken);

			Claims claims = TokenizerService.verifyToken(authtoken);
			String clientId = "user##".concat(String.valueOf(claims.get("id")));
			System.out.println("clientId - "+ clientId);
			Date expireDate = claims.getExpiration();
			Date currentDate;

			if (expireDate != null) {
				currentDate = new Date();
				if (currentDate.equals(expireDate) || currentDate.after(expireDate)) {
					System.out.println("Token expired!!Login Again");
					return;
				}
			}

			String redisToken = RedisService.getToken(clientId);
			if (authtoken.equalsIgnoreCase(redisToken)) {
				System.out.println("Token matched");
				filterChain.doFilter(req, resp);
			} else {
				resp.sendError(404, "Token Invalidated");
				System.out.println("Login Again!!Session Expired");
			}
		} else {
			// resp.sendRedirect("/login");
			// resp.setStatus(404);
			resp.getWriter().write("Token Empty");
			System.out.println("Header empty");
			System.out.println("Login Again!!Session Expired");
		}
		return;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}
}
