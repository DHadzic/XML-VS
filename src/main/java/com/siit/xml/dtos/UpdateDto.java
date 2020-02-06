package com.siit.xml.dtos;

public class UpdateDto {
	String newValue;
	String xPath;
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getxPath() {
		return xPath;
	}
	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	public UpdateDto(String newValue, String xPath) {
		super();
		this.newValue = newValue;
		this.xPath = xPath;

	}
	public UpdateDto() {
		super();
	}
	
	
}
