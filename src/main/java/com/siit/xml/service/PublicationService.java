package com.siit.xml.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.exceptions.NotFoundException;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.model.publication.TReviewer;
import com.siit.xml.repository.PublicationRepository;

@Service
public class PublicationService {
	
	@Autowired PublicationRepository publicationRep;

	public TPublication saveXML(String xmlData) {
		return publicationRep.saveXML(xmlData);
	}
	
	
	public TPublication saveXML(MultipartFile xmlFile) {
		File f= null;
		try {
		    f = new ClassPathResource("data/helpfile.txt").getFile();
		    f.createNewFile();
		    FileOutputStream fos = new FileOutputStream(f); 
			fos.write(xmlFile.getBytes());
		    fos.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new NotFoundException("Bad input file..");
		}
		try{
			return publicationRep.saveXML(f);
		}catch(Exception e){
			throw e;
		}
	}
	public TPublication getById(String id ){
		return publicationRep.getPublicationById(id);
	}
	public void update(String publicationId, String xPath, String newValue){
		publicationRep.updatePublication(newValue, publicationId, xPath);
	}
	public void delete(String publicationId){
		publicationRep.delete(publicationId);
	}


	public TPublication updateStatus(String id, String status) {
		return publicationRep.updateStatus(id, status);
	}


	public TPublication updateReviewers(String id, List<TReviewer> reviewers) {
		return publicationRep.updateReviewers(id, reviewers);
	}
	public List<TPublication> getAll(String username){
		return publicationRep.getAll(username);
	}


	public TPublication withdrawnPublication(String id) {
		return publicationRep.withdrawnPublication(id);
	}


	public List<TPublication> findByStatus(String status) {
		return publicationRep.findByStatus(status);
	}


	public List<TPublication> findByDateAfter(String date) {
		return publicationRep.findByDateAfter(date);
	}


	public List<TPublication> searchByText(String text) {
		return publicationRep.searchByText(text);
	}
	public File getFile(FileGenDTO data){
		return publicationRep.getFile(data);
	}
}
