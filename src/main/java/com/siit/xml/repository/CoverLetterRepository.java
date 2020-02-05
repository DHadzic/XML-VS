package com.siit.xml.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.FileType;
import com.siit.xml.modelUser.User;
import com.siit.xml.utils.GenericFileGen;
import com.siit.xml.utils.MyGenericDatabase;
import com.siit.xml.utils.PDFTransformer;

@Component
public class CoverLetterRepository {

	@Autowired
	MyGenericDatabase db;
	
	@Autowired
	GenericFileGen fileGenerator;

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
	
	public File getFile(FileGenDTO data) {
		User user;

		try {
			if( (user = db.getResourceById(new User(), data.getId())) == null){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(data.getType() == FileType.XML) {
			fileGenerator.generateXMLFile(user);
			return new File(GenericFileGen.XML_LOCATION);
		}else if (data.getType() == FileType.HTML){
			fileGenerator.generateHTMLFile(user);
			return new File(PDFTransformer.HTML_FILE);
		}else if (data.getType() == FileType.PDF){
			fileGenerator.generatePDFFile(user);
			return new File(GenericFileGen.PDF_LOCATION);
		}
		
		return null;
	}
}
