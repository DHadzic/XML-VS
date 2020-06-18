package com.siit.xml.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.AuthorReview;
import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.ReviewDTO;
import com.siit.xml.dtos.ReviewRequestDTO;
import com.siit.xml.modelRequest.ReviewRequest;
import com.siit.xml.modelReview.Authors;
import com.siit.xml.modelReview.ObjectFactory;
import com.siit.xml.modelReview.Review;
import com.siit.xml.repository.ReviewRepository;

@Component
public class ReviewService {

	@Autowired
	ReviewRepository reviewRep;
	
	public String saveXML(String xmlData,String username) {
		return reviewRep.saveXML(xmlData,username);
	}

	public String saveXML(MultipartFile xmlFile,String username) {
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
		return reviewRep.saveXML(f,username);
	}
	
	public String saveReview(ReviewDTO reviewDTO, String user) {
		Review review = new ObjectFactory().createReview();
		
		review.setPaperId(reviewDTO.getPaperId());
		review.setReviewedBy(reviewDTO.getReviewedBy());
		review.setComment(reviewDTO.getComment());
		review.setRateReadability(reviewDTO.getRateReadability());
		review.setRateOriginality(reviewDTO.getRateOriginality());
		review.setRateSubject(reviewDTO.getRateSubject());
		if(reviewDTO.getAuthors() == null) return "Bad input data";
		review.setAuthors(new Authors());
		for (String username : reviewDTO.getAuthors()) {
			review.getAuthors().getUsername().add(username);
		}
		
		return reviewRep.save(review,user);
	}

	public File getFile(FileGenDTO data) {
		return reviewRep.getFile(data);
	}

	public File getFileMerged(FileGenDTO data) {
		return reviewRep.getFileMerged(data);
	}

	public String saveRequest(ReviewRequestDTO requestDto) {
		ReviewRequest request = new ReviewRequest();
		request.setPaperId(requestDto.getPaperId());
		request.setReviewerUsername(requestDto.getUsername());
		
		return reviewRep.saveRequest(request);
	}
	
	public List<AuthorReview> getAuthorReviews(){
		return reviewRep.getAuthorReviews();
	}
	
	public String approveReview(String id) {
		return reviewRep.approveReview(id);
	}
	
	public List<ReviewRequest> getMyRequests(String username){
		return reviewRep.getMyRequests(username);
	}
	
	public String declineRequest(String id) {
		return reviewRep.declineRequest(id);
	}
}