package com.siit.xml.utils.rdf;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;


public class RDFSerialiser {

	private static final String URLBASE = "http://siit.xml/";
	private static final String PRED_URL = URLBASE + "predicates/";

	List<Field> getFieldsAnotatedWith(Class<?> clazz, Class<? extends Annotation> annotation) {
		List<Field> fields = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotation)) {
				fields.add(field);
			}
		}
		return fields;
	}

	Field getFirstFieldAnotatedWith(Class<?> clazz, Class<? extends Annotation> annotation) {
		List<Field> fields = getFieldsAnotatedWith(clazz, annotation);
		return fields.size() > 0 ? fields.get(0) : null;
	}

	public void saveFuseki(Object fusekiObject) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = fusekiObject.getClass();
		if (!clazz.isAnnotationPresent(RDFSerializable.class)) {
			String error = "The class " + clazz.getSimpleName() + " is not annotated with RDFSerializable";
			return;
		}

		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PRED_URL);

		Resource root = getResource(model, fusekiObject);
		addLiteralStatements(model, root, fusekiObject);
		
		
		
		for (Field field : getFieldsAnotatedWith(clazz, RDFProperty.class)) {
			Property property = getProperty(model, field);
			field.setAccessible(true);
			Resource resource = getResource(model, field.get(fusekiObject));
			if(resource==null){
				continue;
			}
			Statement statement = model.createStatement(root, property, resource);
			model.add(statement);

		}
		
		
		
		for (Field field : getFieldsAnotatedWith(clazz, RDFListProperty.class)) {
			RDFListProperty listProp = field.getAnnotation(RDFListProperty.class);
			Property property = getProperty(model, listProp.Predicate());
			field.setAccessible(true);
			//Class<?> valueType = listProp.ValueType();
			if(field.get(fusekiObject) ==null) {
				continue;
			}
			@SuppressWarnings("unchecked")
			List<Object> objects = (List<Object>) field.get(fusekiObject);
			
			for(Object inListObject : objects) {
				
				Resource resource = getResource(model, inListObject);
				Statement statement = model.createStatement(root, property, resource);
				model.add(statement);
			}	

		}
		
		
		
//		System.out.println("[INFO] Rendering the UPDATE model as RDF/XML...");
//		model.write(System.out, "RDF/XML");
//		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);
		SparqlUtil.insertData(clazz.getSimpleName(), new String(out.toByteArray()));
		
		
		

	}

	Property getProperty(Model model, Field field) {
		return model.createProperty(PRED_URL, field.getName());
	}

	Property getProperty(Model model, String pred) {
		return model.createProperty(PRED_URL, pred);
	}

	void addLiteralStatements(Model model, Resource root, Object object)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		for (Field field : getFieldsAnotatedWith(clazz, RDFLiteral.class)) {
			Property property = getProperty(model, field);
			field.setAccessible(true);
			model.add(model.createStatement(root, property, field.get(object).toString()));
		}

	}

	String getUri(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field uriField = getFirstFieldAnotatedWith(clazz, RDFID.class);
		if (uriField == null)
			return null;
		uriField.setAccessible(true);
		return URLBASE + clazz.getSimpleName() + "/" + uriField.get(object).toString();
	}

	Resource getResource(Model model, Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return null;
		}
		String resourceUri = getUri(object);
		if (resourceUri != null) {
			return model.createResource(resourceUri);
		}
		Resource resource;
		resource = model.createResource();
		addLiteralStatements(model, resource, object);
		return resource;
	}

}
