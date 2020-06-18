package com.siit.xml.utils.rdf;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;


public class SparqlUtil {

	/* The following operation causes all of the triples in all of the graphs to be deleted */
	private static final String DROP_ALL = "DROP ALL";
	
	/* Removes all of the triples from a named graphed */
	private static final String DROP_GRAPH_TEMPLATE = "DROP GRAPH <%s>";

	/* Insert RDF data into the default graph */
	private static final String UPDATE_TEMPLATE = "INSERT DATA { %s }";
	
	/* Insert RDF data to an arbitrary named graph */
	private static final String UPDATE_TEMPLATE_NAMED_GRAPH = "WITH <%1$s%2$s> DELETE {?s ?p ?o} WHERE { <%3$s> ?p ?o. ?s ?p ?o };"
			+ " INSERT DATA { GRAPH <%1$s%2$s> { %4$s } }"; //1.DATA_URI 2.GRAPH_NAME 3.ENTITY_ID 4.NTRIPLES
	
	/* Simple SPARQL query on a named graph */
	private static final String SELECT_NAMED_GRAPH_TEMPLATE = "SELECT * FROM <%1$s%2$s> WHERE { %3$s }";
	
	private static final String MY_SELECT = 
			"SELECT DISTINCT ?publication FROM <%1$s%2$s> WHERE\r\n" + 
			"{ \r\n" + 
			"    ?publication <http://siit.xml/predicates/basicInfo> ?basicInfo .\r\n" + 
			"    ?publication <http://siit.xml/predicates/status> ?status .\r\n" + 
			"    ?basicInfo <http://siit.xml/predicates/author> ?author .\r\n" + 
			"    ?basicInfo <http://siit.xml/predicates/keyword> ?keyword .\r\n" + 
			"  	 ?basicInfo <http://siit.xml/predicates/reviewer> ?reviewer .\r\n" + 
			"  	 ?author <http://siit.xml/predicates/authorInstitution> ?authorInstitution .\r\n" + 
			"    FILTER(%3$s)\r\n" + 
			"}";

	
	/* Plain text RDF serialization format */
	public static final String NTRIPLES = "N-TRIPLES";

	/* An XML serialization format for RDF data */
	public static final String RDF_XML = "RDF/XML";
	
	private static final String DATA_URI ="http://localhost:8080/fuseki/PersonDataset/data/";
	private static final String UPDATE_URI ="http://localhost:8080/fuseki/PersonDataset/update";
	private static final String QUERY_URI ="http://localhost:8080/fuseki/PersonDataset/query";
	
	public static String dropAll() {
		return DROP_ALL;
	}
	
	public static String dropGraph(String graphURI) {
		return String.format(DROP_GRAPH_TEMPLATE, graphURI);
	}
	
	/* Inserts data to the default graph */
	public static String insertData(String ntriples) {
		return String.format(UPDATE_TEMPLATE, ntriples);
	}
	
	public static void insertData(String graphName, String EntityIDUri, String ntriples) {
		String sparqlUpdate = String.format(UPDATE_TEMPLATE_NAMED_GRAPH, DATA_URI, graphName, EntityIDUri, ntriples);
		System.out.println(sparqlUpdate);
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, UPDATE_URI);
		processor.execute();
	}
	
	public static void selectData(String graphName, String sparqlCondition) {
		String sparqlQuery = String.format(MY_SELECT, DATA_URI, graphName, sparqlCondition);
		System.out.println(sparqlQuery);
		QueryExecution query = QueryExecutionFactory.sparqlService(QUERY_URI, sparqlQuery);
		ResultSet results = query.execSelect();
		while(results.hasNext()) {
			System.out.println(results.next().get("publication").toString());
		}

	}

}

//SELECT DISTINCT ?publication FROM <http://localhost:8080/fuseki/PersonDataset/data/MainGraph> WHERE
//{ 
//  {
//    ?publication <http://siit.xml/predicates/BasicInfo> ?basicInfo .
//    ?publication <http://siit.xml/predicates/Status> ?status .
//    ?basicInfo <http://siit.xml/predicates/author> ?author .
//    ?basicInfo <http://siit.xml/predicates/keyword> ?keyword .
//  	?author <http://siit.xml/predicates/authorInstituion> ?authorInstitution .
//    FILTER(?author = ?biloSta || ?author = "Pera" )
//  }
//  {
//    
//  }
//}
