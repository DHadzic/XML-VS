package com.siit.xml;

import java.util.ArrayList;

import org.apache.commons.logging.impl.SLF4JLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.rdf.RDFSerialiser;
import com.siit.xml.utils.rdf.SparqlUtil;

@SpringBootApplication
public class XmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(XmlApplication.class, args);
	}
}
