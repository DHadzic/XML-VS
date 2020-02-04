package com.siit.xml.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.MyGenericDatabase;

@Component
public class UserRepository {
	
	@Autowired
	MyGenericDatabase db;
	
	public boolean saveUser(User user) {
		try {
			if(db.getResourceById(new User(), user.getUsername().toString()) != null) {
				return false;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		
		try {
			db.saveResourse(user, user.getUsername().toString());
		}catch ( Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<User> getAll() {
		try {
			return db.getByXPath(new User(), "//user/*/..");			
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUsername(String username) {
		try {
			return db.getResourceById(new User(), username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
