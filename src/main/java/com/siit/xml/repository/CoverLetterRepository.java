package com.siit.xml.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.MyGenericDatabase;

@Component
public class CoverLetterRepository {

	@Autowired
	MyGenericDatabase db;

	// Promeniti na CoverLetter kad se odradi model
	public String saveXML(String xmlData) {
		
		User user = db.getClassFromXML(new User(), xmlData);
		
		try {
			db.saveResourse(user, user.getUsername());
		} catch (Exception e) {
			//e.printStackTrace();
			return "Bad input data";
		}
		return "Succesful";
	}

	// Promeniti na CoverLetter kad se odradi model
	public String saveXML(File xmlData) {
		
		User user = db.getClassFromXML(new User(), xmlData);

		try {
			db.saveResourse(user, user.getUsername().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return "Bad input data";
		}
		
		return "Succesful";
	}
}
