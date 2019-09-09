package com.demo.reservation.security;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.demo.reservation.entity.SecuredUser;
import com.demo.reservation.entity.User;
import com.demo.reservation.repository.UserRepository;



@Component
public class CustomAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String userName = "";
		if (authentication.getPrincipal() instanceof Principal) {
			userName = ((Principal) authentication.getPrincipal()).getName();

		} else {
			userName = ((SecuredUser) authentication.getPrincipal()).getUsername();
		}

		// HttpSession session = request.getSession();

		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new RuntimeException("couldn't find user"));

		httpSession.setAttribute("userId", user.getId());
		response.sendRedirect("/profile");

	}

}
