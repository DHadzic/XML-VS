package com.siit.xml.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;



@Component
public class GenericFileGen {

	public static String XML_LOCATION = "generated/variable.xml";
	public static String PDF_LOCATION = "generated/variable.pdf";
	public static final Map<String,String> xslPathMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","data/xsl/User.xsl");
	}};
	
	
	public <T> void generateXMLFile(T writeValue) {
    	File file = new File(XML_LOCATION);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
		}
    	
    	String className = getClassName(writeValue);
    	String modelPath = MyGenericDatabase.jaxbPathMap.get(className);
    	String schemaPath = MyGenericDatabase.schemaPathMap.get(className);
    	
    	try {
	        JAXBContext context = JAXBContext.newInstance(modelPath);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource(schemaPath).getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(writeValue, file);
	        marshaller.marshal(writeValue, System.out);		
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    	System.out.println("Wrote to XML [" + XML_LOCATION + "] file");
	}
	
	public <T> void generateHTMLFile(T writeValue) {
		
		PDFTransformer pdfTransformer = new PDFTransformer();

		String XSL_LOCATION = xslPathMap.get(getClassName(writeValue));
		try {
			pdfTransformer.generateHTML(XML_LOCATION, XSL_LOCATION);
			pdfTransformer.generatePDF(PDF_LOCATION);		
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	public <T> File generatePDFFile(T writeValue) {
		generateXMLFile(writeValue);
    	File pdfFile = new File(PDF_LOCATION);
    	
		if (!pdfFile.getParentFile().exists()) {
			pdfFile.getParentFile().mkdir();
		}
    	
		PDFTransformer pdfTransformer = new PDFTransformer();
		
		String XSL_LOCATION = xslPathMap.get(getClassName(writeValue));
		try {
			pdfTransformer.generateHTML(XML_LOCATION, XSL_LOCATION);
			pdfTransformer.generatePDF(PDF_LOCATION);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdfFile;
	}
	
    public <T> String getClassName(T myObject) {
    	Class<?> enclosingClass = myObject.getClass().getEnclosingClass();
    	if (enclosingClass != null) {
    		return enclosingClass.getName();
    	} else {
    		return myObject.getClass().getName();
    	}    	
    }

}
