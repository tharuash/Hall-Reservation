package com.demo.reservation.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.reservation.entity.HallRequest;
import com.demo.reservation.entity.User;

public interface HallRequestRepository extends JpaRepository<HallRequest, Integer> {

	List<HallRequest> findAllByUserId(int userId);

	List<HallRequest> findAllByDate(LocalDate date);

	List<HallRequest> findAllByUser(User user);

	List<HallRequest> findAllByOrderByIdAsc();

	List<HallRequest> findAllByUserAndDate(User user, LocalDate date);

}
