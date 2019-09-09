package com.demo.reservation.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.demo.reservation.dto.RegisterUser;
import com.demo.reservation.entity.Role;
import com.demo.reservation.service.HallRequestService;
import com.demo.reservation.service.UserService;



@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private HallRequestService requestService;

	@RequestMapping("/")
	public String index() {
		return "login";
	}

	@RequestMapping("/register")
	public String toRegistrationPage() {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegistration(@ModelAttribute("user") RegisterUser registerUser) {

		if (userService.insert(registerUser) != null) {
			return "redirect:/";
		} else {
			return "redirect:/register?failed";
		}

	}

	@RequestMapping("/profile")
	public String toProfilePage(Model model, @SessionAttribute("userId") int userId) throws ParseException {
		List<Role> userRoles = userService.findById(userId).getRoles();

		for (Role r : userRoles) {

			if (r.getName().contains("ROLE_ADMIN")) {

				model.addAttribute("requests", requestService.findAllByDate());
				return "admin-dashboard";
			}
		}
		model.addAttribute("requests", requestService.findAllByUserId(userId));
		return "user-dashboard";
	}

}
