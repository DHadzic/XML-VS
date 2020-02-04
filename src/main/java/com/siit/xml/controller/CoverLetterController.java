package com.siit.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.siit.xml.service.CoverLetterService;

@RestController
@RequestMapping("api/coverLetter")
public class CoverLetterController {
	
	@Autowired
	CoverLetterService clService;

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/fromXML",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public String handleJson(@RequestBody String xmlData) {
		clService.saveXML(xmlData);
        return "Success";
    }
}
