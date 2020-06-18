package com.siit.xml.utils.rdf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RDFSerializable {
	public String GraphUri() default "Publications";
	public String TypeUri() default "";
}

