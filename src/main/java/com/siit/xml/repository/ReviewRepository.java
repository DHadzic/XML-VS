package com.siit.xml.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.siit.xml.dtos.AuthorReview;
import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.FileType;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.modelRequest.ReviewRequest;
import com.siit.xml.modelReview.Review;
import com.siit.xml.modelReviews.Reviews;
import com.siit.xml.modelReviews.TAuthors;
import com.siit.xml.modelReviews.TReview;
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

	public String saveXML(String xmlData, String currentUser) {
		
		Review review = db.getClassFromXML(new Review(), xmlData);
		
		if(review == null) { return "Bad input data1"; }
		
		if(!checkReferences(review)) { return "References not good"; }

		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data2";
		}
		
		if(!requestExists(review, currentUser)) {
			return "You can't write review";
		}
		
		String id;
		try {
			id = new Integer(db.countResources(new Review())).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		return "Successful";
	}

	public String saveXML(File xmlData, String currentUser) {
		Review review = db.getClassFromXML(new Review(), xmlData);

		if(review == null) { return "Bad input data"; }

		if(!checkReferences(review)) { return "References not good"; }

		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data";
		}

		if(!requestExists(review, currentUser)) {
			return "You can't write review";
		}

		String id;
		try {
			id = new Integer(db.countResources(new Review())).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		
		return "Successful";
	}
	
	public String save(Review review, String currentUser) {
		
		if(!checkReferences(review)) { return "References not good"; }
		if(!db.validateClassAgainstSchema(review)){
			return "Bad input data";
		}
		
		if(!requestExists(review, currentUser)) {
			return "You can't write review";
		}
				
		String id;
		try {
			id = new Integer(db.countResources(new Review())).toString();
			db.saveResourse(review, id);
		} catch (Exception e) {
			return "Something went wrong";
			//e.printStackTrace();
		}
		
		return "Successful";
	}
	
	public File getFile(FileGenDTO data) {
		Review review;

		try {
			if( (review = db.getResourceById(new Review(), data.getId())) == null){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(data.getType() == FileType.XML) {
			fileGenerator.generateXMLFile(review);
			return new File(GenericFileGen.XML_LOCATION);
		}else if (data.getType() == FileType.HTML){
			fileGenerator.generateHTMLFile(review);
			return new File(PDFTransformer.HTML_FILE);
		}else if (data.getType() == FileType.PDF){
			fileGenerator.generatePDFFile(review);
			System.out.println(GenericFileGen.PDF_LOCATION);
			return new File(GenericFileGen.PDF_LOCATION);
		}
		
		return null;
	}

	public File getFileMerged(FileGenDTO data) {
		List<Review> reviews;
		Reviews retReviews = new Reviews();
		
		/*try {
			if(db.getResourceById(new TPublication(), data.getId()) == null){
				return null;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}*/
		
		reviews = getReviewsForPaper(data.getId());

		for (Review rev : reviews) {
			retReviews.getReviews().add(mapRevtoTRev(rev));
		}
		
		if(data.getType() == FileType.XML) {
			fileGenerator.generateXMLFile(retReviews);
			return new File(GenericFileGen.XML_LOCATION);
		}else if (data.getType() == FileType.HTML){
			fileGenerator.generateHTMLFile(retReviews);
			return new File(PDFTransformer.HTML_FILE);
		}else if (data.getType() == FileType.PDF){
			fileGenerator.generatePDFFile(retReviews);
			System.out.println(GenericFileGen.PDF_LOCATION);
			return new File(GenericFileGen.PDF_LOCATION);
		}
		
		return null;
	}
	
	public String saveRequest(ReviewRequest request) {
		String id;
		try {
			id = db.getNewId(request);
		} catch( Exception e) {
			id = "DEFAULT_ID";
		}
		request.setRequestId(id);
		
		if(!db.validateClassAgainstSchema(request)) {
			return "Bad input data";
		}
		
		try {
			if(db.getResourceById(new User(), request.getReviewerUsername()) == null) { return "Invalid username"; }
			//if(db.getResourceById(new TPublication(), request.getPaperId()) == null) { return "Invalid paper id"; }
		} catch (Exception e) {
			return "Invalid data";
		}
		try {
			db.saveResourse(request, id);
		} catch (Exception e) {
			return "Something went wrong";
		}
		
		return "Successful";
	}
	
	public String approveReview(String id) {
		//db.updateResource(new Review(), id, "/Review/@reviewedBy", "reviewer");
		try {
			Review review = db.getResourceById(new Review(), id);
			review.setReviewedBy("reviewer");
			db.saveResourse(review, id);
		} catch ( Exception e) {
			return "Invalid id";
		}
		return "Successful";
	}
	
	public String deleteReview(String id) {
		try {
			Review review = db.getResourceById(new Review(), id);
			if(review != null) {
				db.deleteResource(review, id);
			}
		} catch (Exception e) {
			return "Invalid id";
		}
		return "Successful";
	}

	
	public List<ReviewRequest> getMyRequests(String username){
		String xpath = "/reviewRequest[reviewerUsername=\"" + username +"\"]";
		try {
			return db.getByXPath(new ReviewRequest(), xpath);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	public String declineRequest(String id) {
		db.deleteResource(new ReviewRequest(), id);
		return "Successful";
	}
	
	private boolean checkReferences(Review review) {
		
		try {
			/*
			if(db.getResourceById(new TPublication(), review.getPaperId()) == null) {
				return false;
			}*/
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
	
	private boolean requestExists(Review review, String loggedUser) {
		if(review.getReviewedBy().equals("author")) {
			return true;
		}
		
		ReviewRequest request = null;
		String xpath = "/reviewRequest[paperId=\"" + review.getPaperId() + "\" and ";
		xpath += "reviewerUsername=\"" + loggedUser +"\"]";
		System.out.println(xpath);
		try {
			request = db.getByXPath(new ReviewRequest(), xpath).get(0);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("No request in databse1");
			return false;
		}
		if(request == null) {
			System.out.println("No request in database");
			return false;
		}
		
		db.deleteResource(request,request.getRequestId());
		
		return true;
	}
	
	private TReview mapRevtoTRev(Review review) {
		TReview tReview = new TReview();
		
		tReview.setComment(review.getComment());
		tReview.setPaperId(review.getPaperId());
		tReview.setRateOriginality(review.getRateOriginality());
		tReview.setRateReadability(review.getRateReadability());
		tReview.setRateSubject(review.getRateSubject());
		tReview.setReviewedBy(review.getReviewedBy());
		tReview.setAuthors(new TAuthors());
		for (int i = 0 ; i < review.getAuthors().getUsername().size(); i++) {
			tReview.getAuthors().getUsername().add(review.getAuthors().getUsername().get(i));
		}
		
		return tReview;
	}
	
	public List<AuthorReview> getAuthorReviews(){
		List<AuthorReview> reviews = new ArrayList<AuthorReview>();
		int i = 0;
		for (Review review : getAllReviews()) {
			if(!review.getReviewedBy().equals("author")) { i++; continue; }
			reviews.add(new AuthorReview(Integer.toString(i),review));
			i++;
		}
		
		return reviews;
	}
	
	private List<Review> getReviewsForPaper(String paperId){
		List<Review> reviews = new ArrayList<Review>();
		
		for (Review review : getAllReviews()) {
			if(review.getReviewedBy().equals("author")) continue;
			if(!review.getPaperId().equals(paperId)) continue;
			reviews.add(review);
		}
		
		return reviews;
	}

	private List<Review> getAllReviews(){
		//return db.getByXPath(new Review(), "//Review");
		List<Review> reviews = new ArrayList<Review>();
		Review helpRev = null;
		
		for(int i = 0; i < 1000 ; i++) {
			try {
				helpRev = db.getResourceById(new Review(), Integer.toString(i));
			}catch ( Exception e) {
				break;
			}
			if(helpRev == null) {
				break;
			}
			reviews.add(helpRev);
		}
		
		return reviews;
	}
	
}