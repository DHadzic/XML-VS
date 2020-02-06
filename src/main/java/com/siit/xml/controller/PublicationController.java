package com.siit.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.siit.xml.service.PublicationService;

@RestController
@RequestMapping("api/publication")
public class PublicationController {
	@Autowired 
	PublicationService pService;
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestBody String xmlData) {
		return new ResponseEntity<String>(pService.saveXML(xmlData),HttpStatus.OK);
    }
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/saveXML",
            method = RequestMethod.POST,
            consumes = "multipart/form-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestParam("file") MultipartFile xmlFile) {
		return new ResponseEntity<String>(pService.saveXML(xmlFile),HttpStatus.OK);
    }
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/enitity/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable String id) {
		try{
			return new ResponseEntity<>(pService.getById(id), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }
}
