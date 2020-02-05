package com.siit.xml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.siit.xml.modelUser.User;
import com.siit.xml.utils.rdf.RDFSerialiser;

@SpringBootApplication
public class XmlApplication {

	public static void main(String[] args) {
		// SpringApplication.run(XmlApplication.class, args);
		test();

	}

	static void test() {
		RDFSerialiser ser = new RDFSerialiser();
		User user = new User();
		user.setEmail("email");
		user.setFullName("FN");
		user.setUsername("0");

		User user1 = new User();
		user1.setEmail("email1");
		user1.setFullName("FN1");
		user1.setUsername("1");
		user1.setUser(user);
		try {
			ser.saveFuseki(user);
			ser.saveFuseki(user1);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
