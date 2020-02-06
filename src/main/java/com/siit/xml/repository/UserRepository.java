package com.siit.xml.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.MyGenericDatabase;

@Component
public class UserRepository {
	
	@Autowired
	MyGenericDatabase db;
	
	public String saveUser(User user) {
		try {
			if(db.getResourceById(user,user.getUsername()) != null) {
				return "Username taken";
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return "Something went wrong";
		}
		
		if(!db.validateClassAgainstSchema(user)) {
			return "Bad input data";
		}
		
		try {
			db.saveResourse(user, user.getUsername());
		}catch ( Exception e){
			//e.printStackTrace();
			return "User was not stored";
		}
		return "Succesful";
	}
	
	public List<User> getAll() {
		try {
			return db.getByXPath(new User(), "//user");			
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void removeUser(String username) {
		db.deleteResource(new User(), username);
	}
	
	public void updateUser(String username) {
		db.updateResource(new User(), username, "/user/email", "NOVI*");
	}

	public List<String> getReviewerUsernames() {
		try {
			List<User> users = db.getByXPath(new User(), "//user[role=\"ROLE_EDITOR\" or role=\"ROLE_REVIEWER\"]");
			ArrayList<String> usernames = new ArrayList<String>();
			for (User user : users) {
				usernames.add(user.getUsername());
			}
			return usernames;
		} catch ( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}

	public User findByUsername(String username) {
		try {
			return db.getResourceById(new User(), username);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Finding user EXCEPTION");
			return null;
		}
	}
}
