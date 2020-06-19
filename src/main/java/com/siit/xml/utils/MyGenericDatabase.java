package com.siit.xml.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xml.sax.helpers.DefaultHandler;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;






@Component
public class MyGenericDatabase {

public static final Map<String,String> collectionIdMap = new HashMap<String,String>() {{
	put("com.siit.xml.model.publication.TPublication", "/db/paper_publish/publication");
	put("com.siit.xml.modelUser.User","/db/paper_publish/user");
	put("com.siit.xml.modelCoverLetter.CoverLetter","/db/paper_publish/coverLetter");
	put("com.siit.xml.modelReview.Review","/db/paper_publish/review");
	put("com.siit.xml.modelReviews.Reviews","/db/paper_publish/reviews");
	put("com.siit.xml.modelRequest.ReviewRequest","/db/paper_publish/request");

	}};
	public static final Map<String,String> jaxbPathMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","com.siit.xml.modelUser");
		put("com.siit.xml.modelCoverLetter.CoverLetter","com.siit.xml.modelCoverLetter");
		put("com.siit.xml.modelReview.Review","com.siit.xml.modelReview");
		put("com.siit.xml.modelReviews.Reviews","com.siit.xml.modelReviews");
		put("com.siit.xml.modelRequest.ReviewRequest","com.siit.xml.modelRequest");
		put("com.siit.xml.model.publication.TPublication", "com.siit.xml.model.publication");
		}};
	public static final Map<String,String> schemaPathMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","data/schemas/User.xsd");
		put("com.siit.xml.modelCoverLetter.CoverLetter","data/schemas/CoverLetter.xsd");
		put("com.siit.xml.modelReview.Review","data/schemas/Review.xsd");
		put("com.siit.xml.modelReviews.Reviews","data/schemas/Reviews.xsd");
		put("com.siit.xml.modelRequest.ReviewRequest","data/schemas/ReviewRequest.xsd");
		put("com.siit.xml.model.publication.TPublication", "data/schemas/Publication.xsd");

		}};
	public static final Map<String,String> namespaceMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","http://localhost:8080/User");
		put("com.siit.xml.modelCoverLetter.CoverLetter","http://localhost:8080/CoverLetter");
		put("com.siit.xml.modelCoverReview.Review","http://localhost:8080/Review");
		put("com.siit.xml.modelCoverReviews.Reviews","http://localhost:8080/Reviews");
		put("com.siit.xml.modelRequest.ReviewRequest","http://localhost:8080/ReviewRequest");
		put("com.siit.xml.model.publication.TPublication", "http://foo.bar");
		}};

    
    public <T> void saveResourse(T writeValue, String entityId) throws Exception {
    	String givenClass = getClassName(writeValue);
    	String collectionId = collectionIdMap.get(givenClass);
    	String modelPath = jaxbPathMap.get(givenClass);
    	String schemaPath = schemaPathMap.get(givenClass);
    	ConnectUtil connUtil = new ConnectUtil();
    	
    	Database db = connUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	XMLResource resource = null;
    	Collection col = null;
    	OutputStream os = new ByteArrayOutputStream();
    	
    	try {
    		col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());

    		resource = (XMLResource) col.createResource(entityId, XMLResource.RESOURCE_TYPE);
        	
        	Marshaller marshaller = getMarshaller(modelPath,schemaPath);
        	marshaller.marshal(writeValue, os);

        	resource.setContent(os);
        	col.storeResource(resource);
    	}	finally 
    	{
    		if (resource != null) {
    			try {
    				((EXistResource)resource).freeResources();
    			}catch( XMLDBException xe) {
    				xe.printStackTrace();
    			}
    		}
    		if(col != null) {
    			try {
    				col.close();
    			} catch (XMLDBException xe) {
    				xe.printStackTrace();
    			}
    		}
    	}
    }

    public <T> String getNewId(T writeValue) throws Exception {
    	String givenClass = getClassName(writeValue);
    	String collectionId = collectionIdMap.get(givenClass);
    	String modelPath = jaxbPathMap.get(givenClass);
    	String schemaPath = schemaPathMap.get(givenClass);
    	ConnectUtil connUtil = new ConnectUtil();
    	
    	Database db = connUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	XMLResource resource = null;
    	Collection col = null;
    	OutputStream os = new ByteArrayOutputStream();
    	
		col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
		for (String key : col.listResources()) {
			System.out.println(key);
		}
		return col.createId();
    }
    

    public <T> void deleteResource(T writeValue, String entityId) {
    	String givenClass = getClassName(writeValue);
    	String collectionId = collectionIdMap.get(givenClass);
    	ConnectUtil connUtil = new ConnectUtil();
    	XMLResource resource = null;
    	Collection col = null;
    	
    	try {
    	Database db = connUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	OutputStream os = new ByteArrayOutputStream();
    	
    		col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
        	resource = (XMLResource) col.createResource(entityId, XMLResource.RESOURCE_TYPE);

        	col.removeResource(resource);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}	finally 
    	{
    		if (resource != null) {
    			try {
    				((EXistResource)resource).freeResources();
    			}catch( XMLDBException xe) {
    				xe.printStackTrace();
    			}
    		}
    		if(col != null) {
    			try {
    				col.close();
    			} catch (XMLDBException xe) {
    				xe.printStackTrace();
    			}
    		}
    	}
    }
    
    public <T> T getResourceById(T searchEntity, String entityId) throws Exception {
    	String givenClass = getClassName(searchEntity);
    	String collectionId = collectionIdMap.get(givenClass);
    	String modelPath = jaxbPathMap.get(givenClass);
    	ConnectUtil con = new ConnectUtil();
    	DatabaseTouple dbt;
    	try {


    		dbt = con.getReourceById(collectionId,entityId,AuthenticationUtilities.loadProperties());	

    	} catch ( NullPointerException e) {
    		return null;
    	}
    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
    	try {
        	return (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(dbt.getResource().getContentAsDOM()));
    	}catch ( NullPointerException e) {
    		return null;
    	}
    }
    
    
    public <T> List<T> getByXPath(T searchEntity,String xpath) throws Exception {
    	String givenClass = getClassName(searchEntity);
    	String collectionId = collectionIdMap.get(givenClass);
    	String modelPath = jaxbPathMap.get(givenClass);
    	String namespace = namespaceMap.get(givenClass);
    	ConnectUtil con= new ConnectUtil();
    	
    	
    	Database db = con.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	
    	Collection col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
    	XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService","1.0");
    	xpathService.setProperty("indent", "yes");
    	xpathService.setNamespace("", namespace);
   	
    	ResourceSet result = xpathService.query(xpath);
    	ResourceIterator i = result.getIterator();
    	Resource next = null;
    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
    	
    	ArrayList<T> retValue = new ArrayList<T>();
    	while(i.hasMoreResources()) {
    		try {
    			System.out.println("HEEELOOO");
        		next = i.nextResource();
        		retValue.add((T) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM()));    			
    		} finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
    		}
    	}
    	
    	return retValue;
    }
    
    public <T> int countResources(T entity) {
    	String givenClass = getClassName(entity);
    	String collectionId = collectionIdMap.get(givenClass);
    	ConnectUtil connUtil = new ConnectUtil();
    	XMLResource resource = null;
    	Collection col = null;
    	
    	try {
    	Database db = connUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	OutputStream os = new ByteArrayOutputStream();
    	
		col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
		return col.getResourceCount();
    	} catch (Exception e){
    		return 0;
    	}
    }
    

    public <T> boolean updateResource(T entity,String id, String aimXPath , String newValue) {
    	String givenClass = getClassName(entity);
    	String collectionId = collectionIdMap.get(givenClass);
    	ConnectUtil con= new ConnectUtil();
    	Collection col = null;
    	
    	try {
	    	Database db = con.connectToDatabase(AuthenticationUtilities.loadProperties());
	    	DatabaseManager.registerDatabase(db);
	    	
	    	col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
            col.setProperty("indent", "yes");
	    	XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService","1.0");
            xupdateService.setProperty("indent", "yes");
            XUpdateTemplate.TARGET_NAMESPACE = namespaceMap.get(givenClass);
            Long num = xupdateService.updateResource(id,String.format(XUpdateTemplate.UPDATE, aimXPath, newValue));
            System.out.println(num);

    	} catch( Exception e) {
    		e.printStackTrace();
    		return false;
    	} finally {
        	
            // don't forget to cleanup
            if(col != null) {
                try { 
                	col.close();
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }

    	return true;
    }

    	

    public <T> T getClassFromXML(T myObject, String xml) {
    	String className = getClassName(myObject);
    	String modelPath = jaxbPathMap.get(className);
   	try {
	    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
	    	return (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(new StringReader(xml)));
    	} catch( Exception e) {
    		System.out.println("getClassFromXML String - EXCEPTION");
    		//e.printStackTrace();
    		return null;
    	}
    }
    public <T> T getClassFromXML(T myObject, File xml) {
    	String className = getClassName(myObject);
    	String modelPath = jaxbPathMap.get(className);
    	try {
	    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
	    	return (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(xml));
    	} catch( Exception e) {
    		System.out.println("getClassFromXML file - EXCEPTION");
    		//e.printStackTrace();
    		return null;
    	}
    }

    public <T> String getClassName(T myObject) {
    	Class<?> enclosingClass = myObject.getClass().getEnclosingClass();
    	if (enclosingClass != null) {
    		return enclosingClass.getName();
    	} else {
    		return myObject.getClass().getName();
    	}    	
    }
    
    
    public Marshaller getMarshaller(String modelPath,String schemaPath) throws JAXBException {
    	JAXBContext context = JAXBContext.newInstance(modelPath);
    	Marshaller marshaller = context.createMarshaller();
    	marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    	marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaPath);
    	return marshaller;
    }

    public Unmarshaller getUnmarshaller(String modelPath) throws JAXBException {
    	JAXBContext context = JAXBContext.newInstance(modelPath);
    	Unmarshaller unmarshaller = context.createUnmarshaller();
    	return unmarshaller;
    }
    
    public <T> boolean validateClassAgainstSchema(T myObject) {
    	String className = getClassName(myObject);
    	String modelPath = jaxbPathMap.get(className);
    	System.out.println("modelPath " + modelPath);
    	System.out.println("className " + className);

    	String schemaPath = "src/main/resources/" + schemaPathMap.get(className);
    	System.out.println("shcemaPath " +schemaPath);

    	try {
    		JAXBContext jc = JAXBContext.newInstance(modelPath);
	        JAXBSource source = new JAXBSource(jc, myObject);
	
	        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
	        Schema schema = sf.newSchema(new File(schemaPath)); 
	
    		Marshaller marshaller = jc.createMarshaller();
    		marshaller.setSchema(schema);
    		marshaller.marshal(myObject, new DefaultHandler());
	        //Validator validator = schema.newValidator();
	        //validator.setErrorHandler(null);
	        //validator.validate(source);
	        return true;
    	} catch ( Exception e) {
    		//e.printStackTrace();
    		return false;
    	}
    }

}
