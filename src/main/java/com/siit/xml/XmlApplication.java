package com.siit.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.siit.xml.model.publication.TAuthor;
import com.siit.xml.model.publication.TBasicInformation;
import com.siit.xml.model.publication.TBasicInformation.Title;
import com.siit.xml.model.publication.TPublication;
import com.siit.xml.model.publication.TReviewer;
import com.siit.xml.utils.rdf.RDFSerialiser;
import com.siit.xml.utils.rdf.SparqlUtil;

@SuppressWarnings("deprecation")
@SpringBootApplication
public class XmlApplication {

public static void main(String[] args) {
		
		SpringApplication.run(XmlApplication.class, args);
		
	}

public static void testSparql() {
	RDFSerialiser ser = new RDFSerialiser();
	
	TAuthor author1 = new TAuthor();
	author1.setAuthorInstitution("AuthorInstitution1");
	author1.setUsername("Username1");
	
	TAuthor author2 = new TAuthor();
	author2.setAuthorInstitution("AuthorInstitution2");
	author2.setUsername("Username2");
	
	TReviewer reviewer3= new TReviewer();
	reviewer3.setReviewerUsername("Username3");
	
	TBasicInformation info1 = new TBasicInformation();
	info1.getAuthors().add(author1);
	info1.getAuthors().add(author2);
	info1.getKeywords().add("Keyword1");
	info1.getKeywords().add("Keyword2");
	info1.getReviewers().add(reviewer3);
	info1.setTitle(new Title());
	info1.getTitle().setValue("title1");

	TPublication pub1 = new TPublication();
	pub1.setBasicInformations(info1);
	pub1.setPublicationId("Publikacija1");
	pub1.setStatus("Aktivan");
	
	TReviewer reviewer2= new TReviewer();
	reviewer2.setReviewerUsername("Username2");
	TBasicInformation info2 = new TBasicInformation();
	info2.getAuthors().add(author2);
	info2.getKeywords().add("Keyword2");
	info2.getReviewers().add(reviewer2);
	info2.setTitle(new Title());
	info2.getTitle().setValue("title2");
	
	TPublication pub2 = new TPublication();
	pub2.setBasicInformations(info2);
	pub2.setPublicationId("Publikacija2");
	pub2.setStatus("Aktivan");
	
	try {
		ser.saveFuseki(pub1);
		ser.saveFuseki(pub2);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	SparqlUtil.selectData("Publications", "?reviewer = <http://siit.xml/user/Username2> || ?author = <http://siit.xml/user/Username2>");
}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	        }
	    };
	}
}
