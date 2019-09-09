package com.demo.reservation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.reservation.dto.RegisterUser;
import com.demo.reservation.entity.Role;
import com.demo.reservation.entity.User;
import com.demo.reservation.repository.UserRepository;
import com.demo.reservation.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User insert(RegisterUser registerUser) {
		if (userRepository.existsByUsername(registerUser.getUsername())) {
			return null;
		}

		if (userRepository.existsByEmail(registerUser.getEmail())) {
			return null;
		}

		// Creating user account
		User user = new User(registerUser.getFirstName(), registerUser.getLastName(), registerUser.getUsername(),
				encoder.encode(registerUser.getPassword()), registerUser.getIdNo(), registerUser.getEmail());

		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_USER"));
		user.setRoles(roles);

		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public User update(User user) {
		User updatingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new RuntimeException("user not found"));

		updatingUser.setRoles(null);
		updatingUser.setRoles(user.getRoles());
		System.out.println(updatingUser);
		return userRepository.save(updatingUser);
	}

}
