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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.dtos.CoverLetterDTO;
import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.service.CoverLetterService;

@RestController
@RequestMapping("api/coverLetter")
public class CoverLetterController {
	
	@Autowired
	CoverLetterService clService;

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestBody String xmlData) {
		return new ResponseEntity<String>(clService.saveXML(xmlData),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = "multipart/form-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestParam("file") MultipartFile xmlFile) {
		return new ResponseEntity<String>(clService.saveXML(xmlFile),HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
    @RequestMapping(
    		value = "/save",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody CoverLetterDTO coverLetter) {
    	return new ResponseEntity<String>(clService.saveCoverLetter(coverLetter),HttpStatus.OK);
    }


	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/file",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
	//@ResponseBody
    public FileSystemResource getFile(@RequestBody FileGenDTO data, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		File f = clService.getFile(data);
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
