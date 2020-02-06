
package com.siit.xml.dtos;

import java.util.List;

public class CoverLetterDTO {
	private String content;
	private List<AuthorDTO> authorData;
	private String paperId;
	private String manuscriptTitle;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<AuthorDTO> getAuthorData() {
		return authorData;
	}
	public void setAuthorData(List<AuthorDTO> authorData) {
		this.authorData = authorData;
	}
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public String getManuscriptTitle() {
		return manuscriptTitle;
	}
	public void setManuscriptTitle(String manuscriptTitle) {
		this.manuscriptTitle = manuscriptTitle;
	}
	
	

}
