package com.siit.xml.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.siit.xml.dtos.LoginDTO;
import com.siit.xml.dtos.TokenDTO;
import com.siit.xml.dtos.UserDTO;
import com.siit.xml.modelUser.User;
import com.siit.xml.security.ForbiddenException;
import com.siit.xml.security.JWTUtils;
import com.siit.xml.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
	UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity login(@Valid @RequestBody LoginDTO loginDTO, BindingResult errors) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            authenticationManager.authenticate(token);
            User user = this.userService.findByUsername(loginDTO.getUsername());

            UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
            Long id = Long.parseLong(user.getUsername().toString(),Character.MAX_RADIX);
            TokenDTO userToken = new TokenDTO(jwtUtils.generateToken(details, id));
            return new ResponseEntity<>(userToken, HttpStatus.OK);
        } catch(ForbiddenException ex) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(
    		value = "/register",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity register(@RequestBody UserDTO user) {
    	return new ResponseEntity<String>(userService.registerUser(user),HttpStatus.OK);
    }
    
    
	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/allUsers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAll() {
    	return new ResponseEntity<List<User>>(userService.getAllUsers(),HttpStatus.OK);
    }
    
	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/allReviewers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllReveiwers() {
    	return new ResponseEntity<List<String>>(userService.getAllReviewers(),HttpStatus.OK);
    }
    
	@PreAuthorize("hasAuthority('ROLE_AUTHOR')")
    @RequestMapping(
    		value = "/test_author",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testAuthor() {
    	return new ResponseEntity<String>("Author role",HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_REVIEWER')")
    @RequestMapping(
    		value = "/test_reviewer",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testReviewer() {
    	return new ResponseEntity<String>("Author reviewer",HttpStatus.OK);
    }

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
    @RequestMapping(
    		value = "/test_editor",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testEditor() {
    	return new ResponseEntity<String>("Author editor",HttpStatus.OK);
    }
}
