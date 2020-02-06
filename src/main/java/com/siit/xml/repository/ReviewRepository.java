package com.siit.xml.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.FileType;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.modelCoverLetter.CoverLetter;
import com.siit.xml.modelReview.Review;
import com.siit.xml.modelUser.User;
import com.siit.xml.utils.GenericFileGen;
import com.siit.xml.utils.MyGenericDatabase;
import com.siit.xml.utils.PDFTransformer;

@Component
public class ReviewRepository {

	@Autowired
	MyGenericDatabase db;
	
	@Autowired
	GenericFileGen fileGenerator;

	// Promeniti na CoverLetter kad se odradi model
	public String saveXML(String xmlData) {
		
		Review review = db.getClassFromXML(new Review(), xmlData);
		
		//if(review == null) { return "Bad input data1"; }
		
		if(!checkReferences(review)) { return "References not good"; }

		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data2";
		}

		String id;
		try {
			id = new Integer(db.getByXPath(new Review(), "//Review").size() + 1).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		return "Succesful";
	}

	// Promeniti na CoverLetter kad se odradi model
	public String saveXML(File xmlData) {
		Review review = db.getClassFromXML(new Review(), xmlData);

		if(review == null) { return "Bad input data"; }

		//if(!checkReferences(review)) { return "References not good"; }

		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data";
		}

		String id;
		try {
			id = new Integer(db.getByXPath(new Review(), "//Review").size() + 1).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		
		return "Succesful";
	}
	
	public String save(Review review) {
		
		//if(!checkReferences(review)) { return "References not good"; }
				
		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data";
		}
		
		String id;
		try {
			id = new Integer(db.getByXPath(new Review(), "//Review").size() + 1).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		
		return "Succesful";
	}
	
	public File getFile(FileGenDTO data) {
		CoverLetter coverLetter;

		try {
			if( (coverLetter = db.getResourceById(new CoverLetter(), data.getId())) == null){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(data.getType() == FileType.XML) {
			fileGenerator.generateXMLFile(coverLetter);
			return new File(GenericFileGen.XML_LOCATION);
		}else if (data.getType() == FileType.HTML){
			fileGenerator.generateHTMLFile(coverLetter);
			return new File(PDFTransformer.HTML_FILE);
		}else if (data.getType() == FileType.PDF){
			fileGenerator.generatePDFFile(coverLetter);
			return new File(GenericFileGen.PDF_LOCATION);
		}
		
		return null;
	}
	
	private boolean checkReferences(Review review) {
		
		try {
			if(db.getResourceById(new TPublication(), review.getPaperId()) == null) {
				return false;
			}
			for (String username : review.getAuthors().getUsername()) {
				if(db.getResourceById(new User(), username) == null) {
					return false;
				}				
			}
		
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
