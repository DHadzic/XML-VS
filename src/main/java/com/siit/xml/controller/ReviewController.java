package com.siit.xml.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.AuthorReview;
import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.ReviewDTO;
import com.siit.xml.dtos.ReviewRequestDTO;
import com.siit.xml.modelRequest.ReviewRequest;
import com.siit.xml.security.JWTUtils;
import com.siit.xml.service.ReviewService;

@RestController
@RequestMapping("api/review")
public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	JWTUtils jwt;

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestBody String xmlData, HttpServletRequest request) {
		String token = request.getHeader("Authentication-Token");
		String username = jwt.getUsernameFromToken(token);
		return new ResponseEntity<String>(reviewService.saveXML(xmlData,username),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = "multipart/form-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestParam("file") MultipartFile xmlFile, HttpServletRequest request) {
		String token = request.getHeader("Authentication-Token");
		String username = jwt.getUsernameFromToken(token);
		return new ResponseEntity<String>(reviewService.saveXML(xmlFile,username),HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
    @RequestMapping(
    		value = "/save",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody ReviewDTO reviewDTO, HttpServletRequest request) {
		String token = request.getHeader("Authentication-Token");
		String username = jwt.getUsernameFromToken(token);
    	return new ResponseEntity<String>(reviewService.saveReview(reviewDTO,username),HttpStatus.OK);
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

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/addRequest",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody ReviewRequestDTO requestDTO) {
    	return new ResponseEntity<String>(reviewService.saveRequest(requestDTO),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/getAuthorReviews",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAuthorReviews() {
    	return new ResponseEntity<List<AuthorReview>>(reviewService.getAuthorReviews(),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/approveReview/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity approveReview(@PathVariable String id) {
    	return new ResponseEntity<String>(reviewService.approveReview(id),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/deleteReview/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteReview(@PathVariable String id) {
    	return new ResponseEntity<String>(reviewService.deleteReview(id),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_REVIEWER')")
    @RequestMapping(
    		value = "/getRequests",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyRequests(HttpServletRequest request) {
		String token = request.getHeader("Authentication-Token");
		String username = jwt.getUsernameFromToken(token);
		return new ResponseEntity<List<ReviewRequest>>(reviewService.getMyRequests(username),HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_REVIEWER')")
    @RequestMapping(
    		value = "/declineRequest/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity declineRequest(@PathVariable String id) {
    	return new ResponseEntity<String>(reviewService.declineRequest(id),HttpStatus.OK);
    }

}