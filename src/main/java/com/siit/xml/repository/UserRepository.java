package com.siit.xml.repository;

import org.springframework.stereotype.Component;

import com.siit.xml.dtos.UserDTO;

@Component
public class UserRepository {
	
	
	public boolean saveUser(UserDTO user) {
		return true;
	}
}
