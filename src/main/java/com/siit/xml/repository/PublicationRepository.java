package com.siit.xml.repository;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.FileType;
import com.siit.xml.exceptions.BadRequestException;
import com.siit.xml.exceptions.NotFoundException;
import com.siit.xml.exceptions.UnprocessableEntityException;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.model.publication.TReviewer;
import com.siit.xml.service.EmailService;
import com.siit.xml.utils.GenericFileGen;
import com.siit.xml.utils.MyGenericDatabase;
import com.siit.xml.utils.PDFTransformer;
import com.siit.xml.utils.rdf.RDFSerialiser;

@Component
public class PublicationRepository {
	@Autowired
	MyGenericDatabase db;
	@Autowired
	GenericFileGen fileGenerator;
	@Autowired
	EmailService emailService;
	
	private void saveResource(TPublication publication) throws Exception {
		db.saveResourse(publication, publication.getPublicationId());
		RDFSerialiser.saveFuseki(publication);
	}
	
	public TPublication saveXML(String xmlData) throws UnprocessableEntityException {
		
		TPublication publication = db.getClassFromXML(new TPublication(), xmlData);
		if (publication == null || !db.validateClassAgainstSchema(publication)){
			throw new UnprocessableEntityException("Bad input data");
		}
		
		try {
			saveResource(publication);
			emailService.send("srbulovicdusan@gmail.com", "Naucni rad", "Nucni rad " + publication.getBasicInformations().getTitle().getValue() + " je dodat u sistem.");
		} catch (Exception e) {
			throw new BadRequestException("Unexpected error");
		}
		return publication;
	}
	
	public TPublication saveXML(File xmlData) {
		
		TPublication publication = db.getClassFromXML(new TPublication(), xmlData);
		if (publication == null || !db.validateClassAgainstSchema(publication)){
			throw new UnprocessableEntityException("Bad input data");
		}
		
		try {
			saveResource(publication);
		} catch (Exception e) {
			throw new BadRequestException("Unexpected error");
		}
		return publication;
	}
	public TPublication getPublicationById(String id){
		try{
			TPublication publication = db.getResourceById(new TPublication(), id);
			return publication;
		}catch(Exception e){
			throw new NotFoundException("Publication with id: " + id + " not found");
		}
	}
	public void updatePublication(String value, String publicationId, String xPath){
		if (!db.updateResource(new TPublication(), publicationId, xPath, value)){
			throw new BadRequestException("Error...");
		}
	}
	public void delete(String publicationId){
		try{
			db.deleteResource(new TPublication(), publicationId);
		}catch(Exception e){
			throw new NotFoundException("Publication with id: " + publicationId + " not found");
		}
	}
	public TPublication updateStatus(String id, String status) {
		TPublication publication = this.getPublicationById(id);
		publication.setStatus(status);
		if (!db.validateClassAgainstSchema(publication)){
			throw new UnprocessableEntityException("status not Valid");                                                                            
		}                                                       
		try {                                                              
			saveResource(publication);
			return publication;
		} catch (Exception e) {                                            
			throw new BadRequestException("Unexpected error");             
		}                                                                  
		
	}
	public TPublication updateReviewers(String id, List<TReviewer> reviewers) {
		TPublication publication = this.getPublicationById(id);           
		publication.getBasicInformations().setReviewers(reviewers);                                   
		if (!db.validateClassAgainstSchema(publication)){                 
			throw new UnprocessableEntityException("Selected reviewers not valid");   
        }                                                                 
        try {                                                             
        	saveResource(publication); 
        	return publication;                                           
        } catch (Exception e) {                                           
        	throw new BadRequestException("Unexpected error");            
        }
	}
	
	public List<TPublication> getAll(String username){
		try{
			return db.getByXPath(new TPublication(), "//SciencePaper[@status='accepted' or ./basicInformations/authors[authorUsename='" + username + "']]");
		}catch(Exception e){
			throw new BadRequestException("Something went wrong...");
		}
	}
	public TPublication withdrawnPublication(String id) {
		TPublication publication = null;
		try {
			publication = db.getResourceById(new TPublication(), id);
		} catch (Exception e1) {
			throw new NotFoundException("Publication not found.");

		}
		publication.setStatus("withdrawn");
		if (!db.validateClassAgainstSchema(publication)){
			throw new BadRequestException("Bad input...");
		}
		try {
			saveResource(publication);
		} catch (Exception e) {
			throw new BadRequestException("Bad input...");

		}
		return publication;
	}
	public List<TPublication> findByStatus(String status) {
		try{
			return db.getByXPath(new TPublication(), "//SciencePaper[@status='" + status + "']");
		}catch(Exception e){
			throw new BadRequestException("Something went wrong...");
		}
	}
	public List<TPublication> findByDateAfter(String date) {
		try{
			return db.getByXPath(new TPublication(), "//SciencePaper[number(translate(@created,'-','')) > " + date + "]");
		}catch(Exception e){
			e.printStackTrace();
			throw new BadRequestException("Something went wrong...");
		}
	}
	public List<TPublication> searchByText(String text) {
		try{
			//return db.getByXPath(new TPublication(), "//*[text()[contains(.,'"+ text + "')]]");
			return db.getByXPath(new TPublication(), "//SciencePaper[.//*[contains(text(), '" + text +"')]]");
			//return db.getByXPath(new TPublication(), text);

		}catch(Exception e){
			e.printStackTrace();
			throw new BadRequestException("Something went wrong...");
		}
	}
	public File getFile(FileGenDTO data){
		TPublication publication;

		try {
			if( (publication = db.getResourceById(new TPublication(), data.getId())) == null){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(data.getType() == FileType.XML) {
			fileGenerator.generateXMLFile(publication);
			return new File(GenericFileGen.XML_LOCATION);
		}else if (data.getType() == FileType.HTML){
			fileGenerator.generateHTMLFile(publication);
			return new File(PDFTransformer.HTML_FILE);
		}else if (data.getType() == FileType.PDF){
			fileGenerator.generatePDFFile(publication);
			return new File(GenericFileGen.PDF_LOCATION);
		}
		
		return null;
	}
}

                                                                          