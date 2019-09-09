package com.demo.reservation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.reservation.dto.ChangeRole;
import com.demo.reservation.entity.HallRequest;
import com.demo.reservation.entity.Role;
import com.demo.reservation.entity.Room;
import com.demo.reservation.entity.User;
import com.demo.reservation.service.HallRequestService;
import com.demo.reservation.service.RoleService;
import com.demo.reservation.service.RoomService;
import com.demo.reservation.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private HallRequestService requestService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoomService roomService;

	@GetMapping("/passes")
	public String toViewPasses(Model model) {
		model.addAttribute("requests", requestService.findAll());
		return "admin-passes";
	}

	@GetMapping("/pass/accept/{id}")
	public String acceptPass(@PathVariable("id") String requestId) {
		HallRequest request = new HallRequest();
		request.setId(Integer.valueOf(requestId));
		request.setIsAccepted(true);
		requestService.update(request);

		return "redirect:/admin/passes";
	}

	@GetMapping("/pass/reject/{id}")
	public String rejectPass(@PathVariable("id") String requestId) {
		HallRequest pass = new HallRequest();
		pass.setId(Integer.valueOf(requestId));
		pass.setIsAccepted(false);
		requestService.update(pass);

		return "redirect:/admin/passes";
	}

	@PostMapping("/pass/search")
	public String searchRequestByUsername(Model model, @RequestBody String username) {

		model.addAttribute("requests", requestService.findAllByUser(username.substring(9)));

		return "admin-passes";
	}

	@GetMapping("/users")
	public String toViewUsers(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin-users";
	}

	@GetMapping("/user/{id}")
	public String toViewUserPasses(Model model, @PathVariable("id") String userId) {
		model.addAttribute("requests", requestService.findAllByUserId(Integer.valueOf(userId)));
		model.addAttribute("user", "user");
		model.addAttribute("userId", userId);
		return "admin-passes";
	}

	@PostMapping("/user/{id}")
	public String updateUserRoles(Model model, @ModelAttribute("role") ChangeRole changeRole,
			@PathVariable("id") String userId) {
		User user = new User();
		user.setId(Integer.valueOf(userId));
		Role role = new Role();

		if (changeRole.getRolename().contains("admin")) {
			role = roleService.findByRoleName("ROLE_ADMIN");
			System.out.println("occures............................");

		} else {
			role = roleService.findByRoleName("ROLE_USER");

		}

		List<Role> list = new ArrayList<Role>();
		list.add(role);

		user.setRoles(list);

		userService.update(user);

		model.addAttribute("users", userService.findAll());
		return "admin-users";
	}

	@PostMapping("/user/search/{id}")
	public String searchPassByDate(Model model, @RequestBody String date, @PathVariable("id") String userId) {

		model.addAttribute("passes", requestService.findAllByUserAndDate(date.substring(5), Integer.valueOf(userId)));
		model.addAttribute("user", "user");
		model.addAttribute("userId", userId);
		return "admin-passes";
	}
	
	@GetMapping("/rooms")
	public String getRooms(Model model) {
		model.addAttribute("rooms",roomService.findAll());
		return "admin-rooms";
	}
	
	@GetMapping("/room/add")
	public String toAddRooms() {
		
		return "admin-rooms-add";
	}
	
	@PostMapping("/room/add")
	public String addRoom(Model model, @ModelAttribute("room") Room room) {
		roomService.insert(room);
		
		model.addAttribute("rooms",roomService.findAll());
		return "admin-rooms";
	}
}
