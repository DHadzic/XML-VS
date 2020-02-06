package com.siit.xml.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.model.publication.TPublication;
import com.siit.xml.repository.PublicationRepository;

@Service
public class PublicationService {
	
	@Autowired PublicationRepository publicationRep;

	public String saveXML(String xmlData) {
		return publicationRep.saveXML(xmlData);
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
		return publicationRep.saveXML(f);
	}
	public TPublication getById(String id ){
		return publicationRep.getPublicationById(id);
	}
}
