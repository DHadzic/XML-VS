package com.siit.xml.dtos;

import com.siit.xml.modelReview.Review;

public class AuthorReview {
	private String id;
	private Review review;
	
	public AuthorReview() {}
	
	public AuthorReview(String id, Review review) {
		super();
		this.id = id;
		this.review = review;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
}
