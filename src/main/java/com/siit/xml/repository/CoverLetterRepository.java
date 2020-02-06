package com.siit.xml.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.FileType;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.modelCoverLetter.CoverLetter;
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
		
		CoverLetter coverLetter = db.getClassFromXML(new CoverLetter(), xmlData);

		try {
			if(db.getResourceById(new TPublication(), coverLetter.getPaperId()) == null) {
				return "Invalid paper id";
			}
		} catch (Exception e) {
			return "Invalid paper id";
			//e.printStackTrace();
		}

		String id;
		try {
			id = new Integer(db.getByXPath(new CoverLetter(), "//CoverLetter").size() + 1).toString();
			db.saveResourse(coverLetter, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		return "Succesful";
	}

	// Promeniti na CoverLetter kad se odradi model
	public String saveXML(File xmlData) {
		CoverLetter coverLetter = db.getClassFromXML(new CoverLetter(), xmlData);

		try {
			if(db.getResourceById(new TPublication(), coverLetter.getPaperId()) == null) {
				return "Invalid paper id";
			}
		} catch (Exception e) {
			return "Invalid paper id";
			//e.printStackTrace();
		}

		String id;
		try {
			id = new Integer(db.getByXPath(new CoverLetter(), "//CoverLetter").size() + 1).toString();
			db.saveResourse(coverLetter, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		
		return "Succesful";
	}
	
	public String save(CoverLetter coverLetter) {
		
		try {
			if(db.getResourceById(new TPublication(), coverLetter.getPaperId()) == null) {
				return "Invalid paper id";
			}
		} catch (Exception e) {
			return "Invalid paper id";
			//e.printStackTrace();
		}
		
		String id;
		try {
			id = new Integer(db.getByXPath(new CoverLetter(), "//CoverLetter").size() + 1).toString();
			db.saveResourse(coverLetter, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
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
