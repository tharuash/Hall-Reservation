package com.demo.reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.reservation.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
	
	List<Room> findAll();
	
	Optional<Room> findById(int id);

}
