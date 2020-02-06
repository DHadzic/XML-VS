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

	/**
	 * A template for creating SPARUL (SPARQL Update) query can be found here:
	 * https://www.w3.org/TR/sparql11-update/
	 */
	/* Insert RDF data into the default graph */
	private static final String UPDATE_TEMPLATE = "INSERT DATA { %s }";
	
	/* Insert RDF data to an arbitrary named graph */
	private static final String UPDATE_TEMPLATE_NAMED_GRAPH = "INSERT DATA { GRAPH <%1$s%2$s> { %3$s } }";
	

	/* Simple SPARQL query on a named graph */
	private static final String SELECT_NAMED_GRAPH_TEMPLATE = "SELECT * FROM <%1$s%2$s> WHERE { %3$s }";
	
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
	
	public static void insertData(String EntityUri, String ntriples) {
		String sparqlUpdate = String.format(UPDATE_TEMPLATE_NAMED_GRAPH, DATA_URI, EntityUri, ntriples);
		System.out.println(sparqlUpdate);
		
		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		// UpdateProcessor sends update request to a remote SPARQL update service. 
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, UPDATE_URI);
		processor.execute();
		
		
		
		
	}
	
	public static void selectData(String EntityUri, String sparqlCondition) {
		System.out.println("[INFO] Making sure the changes were made in the named graph \"" + UPDATE_URI + "\".");
		String sparqlQuery = String.format(SELECT_NAMED_GRAPH_TEMPLATE, DATA_URI, EntityUri, sparqlCondition);
		QueryExecution query = QueryExecutionFactory.sparqlService(QUERY_URI, sparqlQuery);
		System.out.println(sparqlQuery);
		// Query the collection, dump output response with the use of ResultSetFormatter
		ResultSet results = query.execSelect();
		ResultSetFormatter.out(System.out, results);
	}

}
