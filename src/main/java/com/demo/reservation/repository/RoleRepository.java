package com.demo.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.reservation.entity.Role;




public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByName(String roleName);
	
	
}
