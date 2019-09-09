package com.demo.reservation.service;

import java.util.List;

import com.demo.reservation.entity.Room;

public interface RoomService {
	
	List<Room> findAll();
	
	Room insert(Room room);

}
