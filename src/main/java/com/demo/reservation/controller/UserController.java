package com.demo.reservation.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.demo.reservation.dto.AddRequest;
import com.demo.reservation.entity.HallRequest;
import com.demo.reservation.entity.Room;
import com.demo.reservation.service.HallRequestService;
import com.demo.reservation.service.RoomService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private HallRequestService requestService;

	@Autowired
	private RoomService roomService;

	@GetMapping("/pass")
	public String toAddPass() {
		return "user-pass";
	}

	@PostMapping("/pass")
	public String getAvailabelHalls(@ModelAttribute("hallrequest") AddRequest addRequest, Model model)
			throws ParseException {
		model.addAttribute("addRequest", addRequest);
		List<HallRequest> currentRequests = requestService.findAllHallRequestsIngGivenPeriod(addRequest);
		List<Room> allRooms = roomService.findAll();

		for (HallRequest h : currentRequests) {
			System.out.println("................................"+h.getRoom().getName());
			if (allRooms.contains(h.getRoom())) {
				allRooms.remove(h.getRoom());
			}
		}
		model.addAttribute("rooms", allRooms);
		
		return "user-pass2";
	}
	
	@PostMapping("/pass/add")
	public String addRequest(@ModelAttribute("hallrequest") AddRequest addRequest, @SessionAttribute("userId") int userId, Model model) throws ParseException {
		requestService.insert(addRequest, userId);
		model.addAttribute("requests", requestService.findAllByUserId(userId));
		return "user-dashboard";
	}
}
