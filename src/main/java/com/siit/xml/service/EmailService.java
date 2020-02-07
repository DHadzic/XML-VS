package com.siit.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoCounter;
@Service
public class EmailService {
	@Autowired
    public JavaMailSender emailSender;
	public void send(String to, String subject, String text) {
	    (new Thread(){
	    	public void run(){
	    		SimpleMailMessage message = new SimpleMailMessage(); 
	            message.setTo(to);
	            message.setSubject(subject); 
	            message.setText(text);
	            emailSender.send(message);
	            System.out.println("sending email...");
	    	}
	    }).start();
		

	}
}
