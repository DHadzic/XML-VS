package com.siit.xml.utils;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

public class DatabaseTouple {


    private Collection collection;
    private XMLResource resource;

    public DatabaseTouple() {
    }

    public DatabaseTouple(Collection collection, XMLResource resource) {
        this.collection = collection;
        this.resource = resource;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection col) {
        this.collection = col;
    }

    public XMLResource getResource() {
        return resource;
    }

    public void setResource(XMLResource resource) {
        this.resource = resource;
    }
}
