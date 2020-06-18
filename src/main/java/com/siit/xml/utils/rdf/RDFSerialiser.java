package com.siit.xml.utils.rdf;

import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

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

	String getTypeUri(Class<?> type) {
		RDFSerializable metaData= type.getAnnotation(RDFSerializable.class);
		if(metaData.TypeUri().equals("")) {
			return type.getSimpleName();
		}
		return metaData.TypeUri();
	}
	public void saveFuseki(Object fusekiObject) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = fusekiObject.getClass();
		if (!clazz.isAnnotationPresent(RDFSerializable.class)) {
			String error = "The class " + clazz.getSimpleName() + " is not annotated with RDFSerializable";
			System.err.println(error);
			return;
		}

		Model model = ModelFactory.createDefaultModel();
		model.setNsPrefix("pred", PRED_URL);
		
		insertObjectIntoModel(model, fusekiObject);
		

		model.write(System.out, SparqlUtil.NTRIPLES);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		model.write(out, SparqlUtil.NTRIPLES);

		RDFSerializable rdfSerializable = clazz.getAnnotation(RDFSerializable.class);
		SparqlUtil.insertData(rdfSerializable.GraphUri(), getUri(fusekiObject), new String(out.toByteArray()));
	}
	
	private Resource insertObjectIntoModel(Model model, Object object) throws IllegalArgumentException, IllegalAccessException {
		Resource root = getResource(model, object);
		
		addLiteralStatements(model, root, object);
		addPropertyStatments(model, root, object);
		return root;
	}

	@SuppressWarnings("unchecked")
	private void addPropertyStatments(Model model, Resource root, Object fusekiObject)
			throws IllegalArgumentException, IllegalAccessException {
		for (Field field : getFieldsAnotatedWith(fusekiObject.getClass(), RDFProperty.class)) {
			field.setAccessible(true);
			if (field.get(fusekiObject) == null) {
				continue;
			}
			Property predicate = getProperty(model, field);
			List<Object> objects;
			if (field.get(fusekiObject) instanceof List<?>) {
				objects = (List<Object>) field.get(fusekiObject);
			} else {
				objects = new ArrayList<>();
				objects.add(field.get(fusekiObject));
			}

			if (field.getAnnotation(RDFReferanceType.class) != null) {
				Class<?> referencedClass = field.getAnnotation(RDFReferanceType.class).getType();
				for (Object inListObject : objects) {
					Resource resource = getReferencedResource(model, referencedClass, inListObject.toString());
					Statement statement = model.createStatement(root, predicate, resource);
					model.add(statement);
				}

			} else {
				for (Object inListObject : objects) {
					
					Resource objectRoot = insertObjectIntoModel(model, inListObject);
					Statement statement = model.createStatement(root, predicate, objectRoot);
					model.add(statement);
				}
			}
		}
	}

	Property getProperty(Model model, Field field) {
		RDFProperty anotation = field.getAnnotation(RDFProperty.class);
		String predicate = "";
		if (anotation != null) {
			predicate = anotation.Predicate();
		} else {
			predicate = field.getAnnotation(RDFLiteral.class).Predicate();
		}
		if (predicate.equals("")) {
			predicate = field.getName();
		}
		return model.createProperty(PRED_URL, predicate);
	}

	Resource getReferencedResource(Model model, Class<?> referencedClass, String id)
			throws IllegalArgumentException, IllegalAccessException {

		return model.createResource(URLBASE + getTypeUri(referencedClass) + "/" + id);
	}

	Resource getResource(Model model, Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return null;
		}
		return model.createResource(getUri(object));
	}

	String getUri(Object object) throws IllegalArgumentException, IllegalAccessException {
		if (object == null) {
			return null;
		}
		Class<?> clazz = object.getClass();
		Field uriField = getFirstFieldAnotatedWith(clazz, RDFID.class);
		uriField.setAccessible(true);
		return URLBASE + getTypeUri(clazz) + "/" + uriField.get(object).toString();
	}

	void addLiteralStatements(Model model, Resource root, Object object)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		for (Field field : getFieldsAnotatedWith(clazz, RDFLiteral.class)) {
			field.setAccessible(true);
			if(field.get(object)==null)
				continue;
			List<Object> objects;
			if (field.get(object) instanceof Collection<?>) {
				objects = (List<Object>) field.get(object);
			} else {
				objects = new ArrayList<>();
				objects.add(field.get(object));
			}
			Property property = getProperty(model, field);
			for (Object inListObject : objects) {
				model.add(model.createStatement(root, property, inListObject.toString()));
			}
		}
	}
}
