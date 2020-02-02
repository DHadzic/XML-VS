package com.siit.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.siit.xml.dtos.UserDTO;
import com.siit.xml.service.UserService;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping("api/user")
public class UserController {

	@Autowired
	UserService userService;

    @RequestMapping(
    		value = "/register",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody UserDTO user) {
    	return new ResponseEntity<String>(userService.registerUser(user),HttpStatus.OK);
    }

}
