package com.demo.reservation.service.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.reservation.dto.AddRequest;
import com.demo.reservation.entity.HallRequest;
import com.demo.reservation.entity.Room;
import com.demo.reservation.entity.User;
import com.demo.reservation.repository.HallRequestRepository;
import com.demo.reservation.repository.RoomRepository;
import com.demo.reservation.repository.UserRepository;
import com.demo.reservation.service.HallRequestService;

@Service
public class HallRequestServiceImpl implements HallRequestService {

	@Autowired
	HallRequestRepository requestRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoomRepository roomRepository;

	@Override
	public List<HallRequest> findAll() {
		return requestRepository.findAllByOrderByIdAsc();
	}

	@Override
	public HallRequest findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public HallRequest insert(AddRequest request, int userId) throws ParseException {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
		LocalDate date = LocalDate.parse(request.getDate());
		LocalTime startTime = LocalTime.parse(request.getStartTime());
		LocalTime endTime = LocalTime.parse(request.getEndTime());
		Room room = roomRepository.findById(Integer.valueOf(request.getRoomId()))
				.orElseThrow(() -> new RuntimeException("room not found"));

		HallRequest hallRequest = new HallRequest();
		hallRequest.setUser(user);
		hallRequest.setDate(date);
		hallRequest.setStartTime(startTime);
		hallRequest.setEndTime(endTime);
		hallRequest.setRoom(room);

		return requestRepository.save(hallRequest);
	}

	@Override
	public List<HallRequest> findAllByUserId(int userId) {
		List<HallRequest> list = requestRepository.findAllByUserId(userId);
		return list;

	}

	@Override
	public HallRequest update(HallRequest request) {
		HallRequest updatingPass = requestRepository.getOne(request.getId());
		updatingPass.setIsAccepted(request.getIsAccepted());
		return requestRepository.save(updatingPass);
	}

	@Override
	public List<HallRequest> findAllByDate() throws ParseException {

		return requestRepository.findAllByDate(LocalDate.now());
	}

	@Override
	public List<HallRequest> findAllByUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("usere not found"));

		return requestRepository.findAllByUser(user);
	}

	@Override
	public List<HallRequest> findAllByUserAndDate(String date, int userId) {
		User user = userRepository.getOne(userId);
		LocalDate issuedDate = LocalDate.parse(date);

		return requestRepository.findAllByUserAndDate(user, issuedDate);
	}

	@Override
	public List<HallRequest> findAllHallRequestsIngGivenPeriod(AddRequest request) throws ParseException {
		LocalDate date = LocalDate.parse(request.getDate());
		LocalTime startTime = LocalTime.parse(request.getStartTime());
		LocalTime endTime = LocalTime.parse(request.getEndTime());

		List<HallRequest> requestInGivenDate = requestRepository.findAllByDate(date);
		if (!requestRepository.findAllByDate(date).isEmpty()) {
			for (Iterator<HallRequest> it = requestInGivenDate.iterator(); it.hasNext();) {
				HallRequest h = it.next();
				if (!((h.getStartTime().compareTo(startTime) <= 0) && (h.getEndTime().compareTo(endTime) >= 0))) {
					it.remove();
				}
			}
		}

		return requestInGivenDate;
	}

}
