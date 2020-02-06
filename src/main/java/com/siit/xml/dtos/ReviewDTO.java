
package com.siit.xml.dtos;

import java.util.List;

public class ReviewDTO {
	private List<String> authors;
	private String comment;
	private String rateSubject;
	private String rateReadability;
	private String rateOriginality;
	private String reviewedBy;
	private String paperId;
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRateSubject() {
		return rateSubject;
	}
	public void setRateSubject(String rateSubject) {
		this.rateSubject = rateSubject;
	}
	public String getRateReadability() {
		return rateReadability;
	}
	public void setRateReadability(String rateReadability) {
		this.rateReadability = rateReadability;
	}
	public String getRateOriginality() {
		return rateOriginality;
	}
	public void setRateOriginality(String rateOriginality) {
		this.rateOriginality = rateOriginality;
	}
	public String getReviewedBy() {
		return reviewedBy;
	}
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
}