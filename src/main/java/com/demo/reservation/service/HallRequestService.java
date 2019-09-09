package com.demo.reservation.service;

import java.text.ParseException;
import java.util.List;

import com.demo.reservation.dto.AddRequest;
import com.demo.reservation.entity.HallRequest;

public interface HallRequestService {
	List<HallRequest> findAll();

	HallRequest findById(int id);

	void delete(int id);

	HallRequest insert(AddRequest registerPass, int userId) throws ParseException;

	HallRequest update(HallRequest hallRequest);

	List<HallRequest> findAllByDate() throws ParseException;

	List<HallRequest> findAllByUserId(int userId);

	List<HallRequest> findAllByUserAndDate(String date, int userId);

	List<HallRequest> findAllHallRequestsIngGivenPeriod(AddRequest request) throws ParseException;
	
	List<HallRequest> findAllByUser(String username);
}
