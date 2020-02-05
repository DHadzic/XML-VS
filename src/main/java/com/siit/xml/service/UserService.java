package com.siit.xml.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.siit.xml.dtos.UserDTO;
import com.siit.xml.modelUser.ObjectFactory;
import com.siit.xml.modelUser.User;
import com.siit.xml.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRep;
	
	public User findByUsername(String username) {
		return userRep.findByUsername(username);
	}
	
	public String registerUser(UserDTO userDTO) {
		User user = (new ObjectFactory()).createUser();
		user.setUsername(userDTO.getUsername());
    	BCryptPasswordEncoder coder = new BCryptPasswordEncoder();
		user.setPassword(coder.encode(userDTO.getPassword()));
		user.setFullName(userDTO.getFullname());
		user.setEmail(userDTO.getEmail());
		user.setRole(userDTO.getRole());
				
		return userRep.saveUser(user);
	}
	
	public List<User> getAllUsers(){
		return userRep.getAll();
	}

	public List<String> getAllReviewers(){
		return userRep.getReviewerUsernames();
	}

}
