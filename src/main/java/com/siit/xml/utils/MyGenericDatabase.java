package com.siit.xml.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

@Component
public class MyGenericDatabase {

	public static final Map<String,String> collectionIdMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","/db/paper_publish/user");
		}};
	public static final Map<String,String> jaxbPathMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","com.siit.xml.modelUser");
		}};
	public static final Map<String,String> schemaPathMap = new HashMap<String,String>() {{
		put("com.siit.xml.modelUser.User","data/schemas/User.xsd");
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
    	}	finally {
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
    	DatabaseTouple dbt = con.getReourceById(collectionId,entityId,AuthenticationUtilities.loadProperties());
    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
    	return (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(dbt.getResource().getContentAsDOM()));
    }
    
    public <T> List<T> getByXPath(T searchEntity,String xpath) throws Exception {
    	String givenClass = getClassName(searchEntity);
    	String collectionId = collectionIdMap.get(givenClass);
    	String modelPath = jaxbPathMap.get(givenClass);
    	ConnectUtil con= new ConnectUtil();
    	
    	Database db = con.connectToDatabase(AuthenticationUtilities.loadProperties());
    	DatabaseManager.registerDatabase(db);
    	
    	Collection col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
    	XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService","1.0");
    	xpathService.setProperty("indent", "yes");
    	
    	ResourceSet result = xpathService.query(xpath);
    	ResourceIterator i = result.getIterator();
    	Resource next = null;
    	Unmarshaller unmarshaller = getUnmarshaller(modelPath);
    	
    	ArrayList<T> retValue = new ArrayList<T>();
    	while(i.hasMoreResources()) {
    		try {
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

}
