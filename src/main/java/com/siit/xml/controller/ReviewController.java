package com.siit.xml.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.ReviewDTO;
import com.siit.xml.service.ReviewService;

@Component
@RequestMapping("api/review")
public class ReviewController {
	@Autowired
	ReviewService reviewService;

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestBody String xmlData) {
		return new ResponseEntity<String>(reviewService.saveXML(xmlData),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = "multipart/form-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestParam("file") MultipartFile xmlFile) {
		return new ResponseEntity<String>(reviewService.saveXML(xmlFile),HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
    @RequestMapping(
    		value = "/save",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody ReviewDTO reviewDTO) {
    	return new ResponseEntity<String>(reviewService.saveReview(reviewDTO),HttpStatus.OK);
    }


	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/file",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
    public FileSystemResource getFile(@RequestBody FileGenDTO data, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		File f = reviewService.getFile(data);
		if(f == null) {
			return null;
		}
		try {
			InputStream stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return null;
		}
		return new FileSystemResource(f);
	}

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/fileMerged",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
    public FileSystemResource getFileMerged(@RequestBody FileGenDTO data, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		File f = reviewService.getFileMerged(data);
		if(f == null) {
			return null;
		}
		try {
			InputStream stream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			return null;
		}
		return new FileSystemResource(f);
	}

}

