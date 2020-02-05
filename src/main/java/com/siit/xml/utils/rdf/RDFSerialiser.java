package com.siit.xml.utils.rdf;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;


public class RDFSerialiser {

	private static final String URLBASE = "http://www.ftn.uns.ac.rs/rdf/examples/";
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

	public void saveFuseki(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		if (!clazz.isAnnotationPresent(RDFSerializable.class)) {
			String error = "The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable";
			return;
		}
		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PRED_URL);

		Resource root = getResource(model, object);
		addLiteralStatements(model, root, object);
		
		for (Field field : getFieldsAnotatedWith(clazz, RDFProperty.class)) {
			Property property = getProperty(model, field, object);
			field.setAccessible(true);
			Resource resource = getResource(model, field.get(object));
			if(resource==null){
				continue;
			}
			Statement statement = model.createStatement(root, property, resource);
			model.add(statement);
		}
		System.out.println("[INFO] Rendering the UPDATE model as RDF/XML...");
		model.write(System.out, "RDF/XML");
	}

	String getUri(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field uriField = getFirstFieldAnotatedWith(clazz, RDFUri.class);
		if (uriField == null)
			return null;
		uriField.setAccessible(true);
		return URLBASE+clazz.getSimpleName()+"/"+uriField.get(object).toString();
	}

	Resource getResource(Model model, Object object) throws IllegalArgumentException, IllegalAccessException {
		if(object==null) {
			return null;
		}
		String resourceUri = getUri(object);
		Resource resource;
		if(resourceUri!=null) {
			return model.createResource(resourceUri);
		}
		resource = model.createResource();
		addLiteralStatements(model, resource, object);
		return resource;
	}

	Property getProperty(Model model, Field field, Object object) {
		return model.createProperty(PRED_URL, field.getName());
	}

	void addLiteralStatements(Model model, Resource root, Object object)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		for (Field field : getFieldsAnotatedWith(clazz, RDFLiteral.class)) {
			Property property = getProperty(model, field, object);
			field.setAccessible(true);
			model.add(model.createStatement(root, property, field.get(object).toString()));
		}

	}

}
