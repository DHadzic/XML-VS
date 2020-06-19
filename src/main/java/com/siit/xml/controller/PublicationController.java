package com.siit.xml.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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

import com.siit.xml.dtos.FileGenDTO;
import com.siit.xml.dtos.UpdateDto;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.model.publication.TReviewer;
import com.siit.xml.service.PublicationService;
import com.siit.xml.utils.rdf.SparqlUtil;

@RestController
@RequestMapping("api/publication")
public class PublicationController {
	@Autowired 
	PublicationService pService;
	@RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestBody String xmlData) {
		return new ResponseEntity<TPublication>(pService.saveXML(xmlData),HttpStatus.OK);
    }
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/xml",
            method = RequestMethod.POST,
            consumes = "multipart/form-data",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveXML(@RequestParam("file") MultipartFile xmlFile) {
		return new ResponseEntity<TPublication>(pService.saveXML(xmlFile),HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/xml",
            method = RequestMethod.PUT,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody String xmlData) {
		return new ResponseEntity<TPublication>(pService.saveXML(xmlData),HttpStatus.OK);
    }
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findById(@PathVariable String id) {
		return new ResponseEntity<>(pService.getById(id), HttpStatus.OK);
    }
	@RequestMapping(
            method = RequestMethod.PUT,
            value="/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePub(@RequestBody UpdateDto dto, @PathVariable String id) {
			pService.update(id, dto.getxPath(), dto.getNewValue());
			return new ResponseEntity<>(HttpStatus.OK);
    }
	@RequestMapping(
            method = RequestMethod.DELETE,
            value="/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePub(@PathVariable String id) {
			pService.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
    }
	//@PreAuthorize("ROLE_EDITOR")
	@RequestMapping(
            method = RequestMethod.PUT,
            value="/{id}/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TPublication> acceptOrReject(@PathVariable String id, @RequestParam Boolean accepted) {
			if (accepted){
				return new ResponseEntity<>(pService.updateStatus(id, "accepted"), HttpStatus.OK);
			}else{
				return new ResponseEntity<>(pService.updateStatus(id, "rejected"), HttpStatus.OK);
			}
    }
	@RequestMapping(
			method = RequestMethod.PUT,
            value="/{id}/reviewers",
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TPublication> addReviewers(@PathVariable String id, @RequestBody List<TReviewer> reviewers){
		for (TReviewer rev : reviewers){
			System.out.println(rev.getReviewerUsername());
		}
		TPublication publication = pService.updateReviewers(id, reviewers);
		return new ResponseEntity<TPublication>(publication, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TPublication>> getAll(Principal principal){
		return new ResponseEntity<>(pService.getAll(principal.getName()), HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			method = RequestMethod.PUT,
			value="/withdrawn/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity withdrawnPublication(@PathVariable String id){
		return new ResponseEntity<TPublication>(pService.withdrawnPublication(id), HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			method = RequestMethod.GET,
			value="/search/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TPublication>> getPublicationByStatus(@RequestParam("status") String status){
		return new ResponseEntity<>(pService.findByStatus(status), HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			method = RequestMethod.GET,
			value="/search/dateAfter",
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TPublication>> getPublicationByDateCreatedAfter(@RequestParam("date") String date){
		date = date.replace("-", "");
		return new ResponseEntity<>(pService.findByDateAfter(date), HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			method = RequestMethod.GET,
			value="/search/text",
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TPublication>> searchPublicationByText(@RequestParam("searchParam") String text){
		System.out.println(text);
		return new ResponseEntity<>(pService.searchByText(text), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
	@RequestMapping(
			value = "/file",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public FileSystemResource getFile(@RequestBody FileGenDTO data, HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		File f = pService.getFile(data);
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
	
	@RequestMapping(
			method = RequestMethod.GET,
			value="/search/metadata",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TPublication>> getPublicationsByMetadata(@RequestBody String query, Principal principal){
		String username = principal.getName();
		List<TPublication> rets = new ArrayList<>();
		for (String publicationID : SparqlUtil.selectData("Publications", query, username)) {
			rets.add(pService.getById(publicationID));
		}
		return new ResponseEntity<>(rets, HttpStatus.OK);
			
	}
	
	
	
}
