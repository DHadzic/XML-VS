package com.siit.xml.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.MyGenericDatabase;

@Component
public class CoverLetterRepository {

	@Autowired
	MyGenericDatabase db;

	public String saveXML(String xmlData) {
		
		User user = db.getClassFromXML(new User(), xmlData);
		
		if(user == null) {
			System.out.println("nah");
		}else {
			System.out.println("Good");
		}
		
		return "Did something";
	}
}
