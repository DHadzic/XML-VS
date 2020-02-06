package com.siit.xml.utils;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
@Component
public class ConnectUtil {

	public DatabaseTouple getReourceById(String collectionId, String documentId, AuthenticationUtilities.ConnectionProperties conn) throws ClassNotFoundException,
		IllegalAccessException, InstantiationException, XMLDBException {
		Class<?> cl = Class.forName(conn.driver);
		
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		
		DatabaseManager.registerDatabase(database);
		
		Collection col = null;
	    XMLResource res = null;
		
		try {
			col = DatabaseManager.getCollection(conn.uri + collectionId);
			if(col == null) {
				
			}
	        col.setProperty(OutputKeys.INDENT, "yes");
	        res = (XMLResource)col.getResource(documentId);
		
		    if (res == null) {
		    	return null;
		    } else {
		        return new DatabaseTouple(col,res);
		    }
	
		} catch(Exception e){ /*e.printStackTrace();*/ return null; }
	    finally {
	        if(res != null) {
	            try {
	                ((EXistResource)res).freeResources();
	            } catch (XMLDBException xe) {
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
	
	public Database connectToDatabase(AuthenticationUtilities.ConnectionProperties conn) throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
		Class<?> cl = Class.forName(conn.driver);
		
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
        return database;
	}
	
	public static Collection getOrCreateCollection(String collectionUri, int offset,
        AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
		Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

		// create the collection if it does not exist
        if(col == null) {
        
         	if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            
        	String pathSegments[] = collectionUri.split("/");
            
        	if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
            
                for(int i = 0; i <= offset; i++) {
                    path.append("/" + pathSegments[i]);
                }
                
                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);
                
                if (startCol == null) {
                	
                	// child collection does not exist
                    
                	String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);
                    
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    
                    col = mgt.createCollection(pathSegments[offset]);
                    
                    col.close();
                    parentCol.close();
                    
                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++offset, conn);
        } else {
            return col;
        }
	}
	
}
