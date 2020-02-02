package com.siit.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siit.xml.dtos.UserDTO;
import com.siit.xml.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRep;
	
	public String registerUser(UserDTO user) {
		if(userRep.saveUser(user)) {
			return "Succesful";
		}else {
			return "Not good";
		}
	}

}
