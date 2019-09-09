package com.demo.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.reservation.entity.Room;
import com.demo.reservation.repository.RoomRepository;
import com.demo.reservation.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Override
	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	@Override
	public Room insert(Room room) {
		return roomRepository.save(room);
	}

}
