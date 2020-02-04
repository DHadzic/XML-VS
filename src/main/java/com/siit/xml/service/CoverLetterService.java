package com.siit.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siit.xml.repository.CoverLetterRepository;

@Service
public class CoverLetterService {

	@Autowired
	CoverLetterRepository clRep;
	
	public String saveXML(String xmlData) {
		clRep.saveXML(xmlData);
		return "Good";
	}

}
