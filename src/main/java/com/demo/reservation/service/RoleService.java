package com.demo.reservation.service;

import java.util.List;

import com.demo.reservation.entity.Role;



public interface RoleService {
	
	List<Role> findAll();

    Role findByRoleName(String roleName);

    void delete(int id);

    Role insert(Role role);
}
