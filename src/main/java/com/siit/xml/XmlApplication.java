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
		// SpringApplication.run(XmlApplication.class, args);
		try {
			test();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static void test() throws IllegalArgumentException, IllegalAccessException {
		RDFSerialiser ser = new RDFSerialiser();
		User user = new User();
		user.setEmail("GLAVNI");
		user.setFullName("j a");
		user.setUsername("carina");
		ser.saveFuseki(user);
		user.users=new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			User user1 = new User();
			user1.setEmail("email1"+i);
			user1.setFullName("FN1"+i);
			user1.setUsername(""+i);
			user.users.add(user1);
			ser.saveFuseki(user1);
		}

		
			ser.saveFuseki(user);
			SparqlUtil.selectData(User.class.getSimpleName(), "?s ?p ?o");

	}

}
