package com.siit.xml.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.AuthorDTO;
import com.siit.xml.dtos.CoverLetterDTO;
import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.modelCoverLetter.AuthorData;
import com.siit.xml.modelCoverLetter.CoverLetter;
import com.siit.xml.modelCoverLetter.ObjectFactory;
import com.siit.xml.repository.ReviewRepository;

@Component
public class ReviewService {

	@Autowired
	ReviewRepository reviewRep;
	
	public String saveXML(String xmlData) {
		return reviewRep.saveXML(xmlData);
	}

	public String saveXML(MultipartFile xmlFile) {
		File f= null;
		try {
		    f = new ClassPathResource("data/helpfile.txt").getFile();
		    f.createNewFile();
		    FileOutputStream fos = new FileOutputStream(f); 
			fos.write(xmlFile.getBytes());
		    fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			return "Bad input file";
		}
		return reviewRep.saveXML(f);
	}
	
	public String saveReview(CoverLetterDTO coverLetterDTO) {
		CoverLetter coverLetter = new ObjectFactory().createCoverLetter();
		coverLetter.setContent(coverLetterDTO.getContent());
		coverLetter.setManuscriptTitle(coverLetterDTO.getManuscriptTitle());
		coverLetter.setPaperId(coverLetterDTO.getPaperId());
		AuthorData auth = null;
		for (AuthorDTO author : coverLetterDTO.getAuthorData()) {
			auth = new AuthorData();
			if(author.getAuthorsName() != null ) auth.setAuthorsName(author.getAuthorsName());
			if(author.getAuthorsEmail() != null ) auth.setAuthorsEmail(author.getAuthorsEmail());
			if(author.getAuthorsPhone() != null ) auth.setAuthorsPhone(author.getAuthorsPhone());
			if(author.getAuthorsAddress() != null ) auth.setAuthorsAddress(author.getAuthorsAddress());
			coverLetter.getAuthorData().add(auth);
		}
		
		return "";//reviewRep.save(coverLetter);
	}

	public File getFile(FileGenDTO data) {
		return reviewRep.getFile(data);
	}

}
