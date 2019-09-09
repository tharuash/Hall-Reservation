package com.demo.reservation.service;

import java.util.List;

import com.demo.reservation.dto.RegisterUser;
import com.demo.reservation.entity.User;




public interface UserService {
	
	List<User> findAll();

    User findById(int id);

    void delete(int id);

    User insert(RegisterUser registerUser);
    
    User findByUsername(String username);
    
    User update(User user);

}
