package com.palm.cloud.services.cmdb.resource;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.web.context.ContextLoaderListener;

import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.condition.LogicalCondition;
import com.palm.cloud.services.cmdb.condition.ValueCondition;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaStatus;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class ResourceTests extends JerseyTest {

	private WebResource r;
	
    public ResourceTests() throws Exception {
    	r = resource();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Override
    protected AppDescriptor configure() {
    	ClientConfig clientConfig = new DefaultClientConfig();
    	clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, 
    			Boolean.TRUE);
    	AppDescriptor appDescriptor = new WebAppDescriptor
			.Builder()
	        .contextPath("oocmdb")
	        .contextParam("contextConfigLocation", 
	        		"file:src/test/resources/applicationContext-test.xml")
	        .servletClass(SpringServlet.class)
	        .initParam(JSONConfiguration.FEATURE_POJO_MAPPING, 
	        		Boolean.TRUE.toString())
	        .servletPath("rs")
	        .clientConfig(clientConfig)
	        .contextListenerClass(ContextLoaderListener.class)
	        .build();
    	return appDescriptor;
    }

    @Test
    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl")
        	.accept(MediaTypes.WADL)
        	.get(String.class);
        Assert.assertTrue(serviceWadl.length() > 0);
    }

    @Test
    public void testMetaStatus() {
    	String status = "live";
    	String uri = "meta/statuses/" + status;
    	r.path(uri).post();
    	MetaStatus selected = r.path(uri)
    		.accept(MediaType.APPLICATION_JSON)
    		.get(MetaStatus.class);
    	Assert.assertEquals(status, selected.getName());
    	r.path(uri).delete();
    	thrown.expect(UniformInterfaceException.class);
    	selected = r.path(uri)
    		.accept(MediaType.APPLICATION_JSON)
			.get(MetaStatus.class);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testSearchResource() {
    	String uri = "data/search/type/Location";
    	ValueCondition condition1 = new ValueCondition(
    			"country", "equal", "USA");
    	LogicalCondition condition2 = new LogicalCondition("and");
    	ValueCondition condition3 = new ValueCondition(
    			"state", "notEqual", "CA");
    	ValueCondition condition4 = new ValueCondition(
    			"location", "like", "ORD");
    	condition2.setConditions(Arrays.asList(
    			new Condition[]{condition3, condition4}));
    	Condition[] conditions = {condition1, condition2};
    	List<CIObject> response = r.path(uri)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(List.class, conditions);
    	Assert.assertNotNull(response);
    }
    
    @Test
    public void testObjectResource() {
    	String statusesURI = "meta/statuses/";
    	String typesURI = "meta/types/";
    	String objectsURI = "data/objects";
    	String objectURI = "data/object/";
    	String name = "10.1.1.255";
    	String namespace = "DEFAULT";
    	String type = "server";
    	String status = "live";
    	
    	//Add status
    	r.path(statusesURI + status).post();
    	
    	//Add type
    	MetaClass typeKlass = new MetaClass();
    	typeKlass.setName(type);
    	r.path(typesURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post(typeKlass);
    	
    	//Add object
    	CIObject objectToAdd = new CIObject(name, namespace, type, status);
    	
    	//Post the object without expecting any response
    	r.path(objectsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(objectToAdd);
    	
    	//Retrieve the object
    	CIObject objectAdded = r.path(objectURI + name)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.get(CIObject.class);
    	Assert.assertNotNull(objectAdded);
    	print(objectAdded);
    	
    	//Delete the object
    	r.path(objectURI + name).delete();
    	
    	//Delete the type
    	r.path(typesURI + type).delete();
    	
    	//Delete the status
    	r.path(statusesURI + status).delete();
    }

    @Test
    public void testUpdateObjectResource() {
    	String statusesURI = "meta/statuses/";
    	String typesURI = "meta/types/";
    	String objectsURI = "data/objects";
    	String objectURI = "data/object/";
    	String name = "Hercules";
    	String namespace = "DEFAULT";
    	String type = "Bicycle";
    	String attribute1 = "brand";
    	String attribute2 = "size";
    	String status = "live";
    	
    	//Add status
    	r.path(statusesURI + status).post();
    	
    	//Add type
    	MetaClass typeKlass = new MetaClass();
    	typeKlass.setName(type);
    	typeKlass.addAttribute(new MetaAttribute(attribute1));
    	typeKlass.addAttribute(new MetaAttribute(attribute2));
    	r.path(typesURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post(typeKlass);
    	
    	//Add object
    	CIObject objectToAdd = new CIObject(name, namespace, type, status);
    	objectToAdd.addAttribute(new CIAttribute(attribute1, name));
    	
    	//Post the object without expecting any response
    	r.path(objectsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(objectToAdd);
    	
    	//Retrieve the object
    	CIObject objectAdded = r.path(objectURI + name)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.get(CIObject.class);
    	Assert.assertNotNull(objectAdded);
    	print(objectAdded);
    	
    	//Update the same object by adding a new attribute
    	CIObject objectToUpdate = new CIObject(name, namespace, type, status);
    	objectToUpdate.addAttribute(new CIAttribute(attribute1, name));
    	objectToUpdate.addAttribute(new CIAttribute(attribute2, "22"));
    	r.path(objectsURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.put(objectToUpdate);
    	
    	//Retrieve the object
    	CIObject objectUpdated = r.path(objectURI + name)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.get(CIObject.class);
    	Assert.assertNotNull(objectUpdated);
    	print(objectUpdated);
    	
    	//Update the object by updating an attribute & removing an attribute
    	objectToUpdate = new CIObject(name, namespace, type, status);
    	objectToUpdate.addAttribute(new CIAttribute(attribute1, "Trek"));
    	r.path(objectsURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.put(objectToUpdate);
    	
    	//Retrieve the object
    	objectUpdated = r.path(objectURI + name)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.get(CIObject.class);
    	Assert.assertNotNull(objectUpdated);
    	print(objectUpdated);
    	
    	//Delete the object
    	r.path(objectURI + name).delete();
    	
    	//Delete the type
    	r.path(typesURI + type).delete();
    	
    	//Delete the status
    	r.path(statusesURI + status).delete();
    }

    @Test
    public void testUpdateRelationResource() {
    	String statusesURI = "meta/statuses/";
    	String typesURI = "meta/types/";
    	String objectsURI = "data/objects";
    	String objectURI = "data/object/";
    	String metaRelationshipsURI = "/meta/relationships/";
    	String relationsURI = "data/relations";
    	String relationURI = "data/relation/";
    	String namespace = "DEFAULT";
    	String fromObject = "Hercules";
    	String fromType = "Bicycle";
    	String toObject = "ECR";
    	String toType = "Road";
    	String attribute1 = "brand";
    	String attribute2 = "size";
    	String relType = "runsOn";
    	String relAttribute1 = "speed";
    	String relAttribute2 = "direction";
    	String status = "live";
    	
    	//Add status
    	r.path(statusesURI + status).post();
    	
    	//Add from type
    	MetaClass fromTypeKlass = new MetaClass();
    	fromTypeKlass.setName(fromType);
    	fromTypeKlass.addAttribute(new MetaAttribute(attribute1));
    	fromTypeKlass.addAttribute(new MetaAttribute(attribute2));
    	r.path(typesURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post(fromTypeKlass);
    	
    	//Add to type
    	MetaClass toTypeKlass = new MetaClass();
    	toTypeKlass.setName(toType);
    	r.path(typesURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post(toTypeKlass);
    	
    	//Add relationship type
    	MetaClass relTypeKlass = new MetaClass();
    	relTypeKlass.setName(relType);
    	relTypeKlass.setRelationshipType(true);
    	relTypeKlass.addAttribute(new MetaAttribute(relAttribute1));
    	relTypeKlass.addAttribute(new MetaAttribute(relAttribute2));
    	r.path(typesURI)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post(relTypeKlass);
    	
    	//Define meta class relationships
    	r.path(metaRelationshipsURI + fromType + "/" + relType + "/" + toType)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.post();
    	
    	//Add from object
    	CIObject fromObject1 = new CIObject(fromObject, namespace, fromType, 
    			status);
    	fromObject1.addAttribute(new CIAttribute(attribute1, "Hercules"));
    	fromObject1.addAttribute(new CIAttribute(attribute2, "22"));
    	
    	//Post the object without expecting any response
    	r.path(objectsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(fromObject1);
    	
    	//Add to object
    	CIObject toObject1 = new CIObject(toObject, namespace, toType, status);
    	
    	//Post the object without expecting any response
    	r.path(objectsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(toObject1);
    	
    	//Add relationship object
    	CIRelationship relObject1 = new CIRelationship(namespace, fromObject, 
    			toObject, relType, status);
    	relObject1.addAttribute(new CIRelationshipAttribute(relAttribute1, 
    			"10"));
    	
    	//POST the relationship without expecting any response
    	r.path(relationsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(relObject1);
    	
    	//Get the relationship
    	CIRelationship selected = r.path(
    			relationURI + fromObject + "~" + relType + "~" + toObject)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.get(CIRelationship.class);
    	Assert.assertNotNull(selected);
    	Assert.assertEquals(1, selected.getAttributes().size());
    	print(selected);
    	
    	//Update relationship object
    	CIRelationship relObjectToUpdate = new CIRelationship(namespace, 
    			fromObject, toObject, relType, status);
    	relObjectToUpdate.addAttribute(new CIRelationshipAttribute(
    			relAttribute1, "20"));
    	relObjectToUpdate.addAttribute(new CIRelationshipAttribute(
    			relAttribute2, "East"));
    	
    	//PUT the relationship without expecting any response
    	r.path(relationsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.put(relObjectToUpdate);
    	
    	//Get the relationship
    	selected = r.path(
    			relationURI + fromObject + "~" + relType + "~" + toObject)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.get(CIRelationship.class);
    	Assert.assertNotNull(selected);
    	Assert.assertEquals(2, selected.getAttributes().size());
    	print(selected);
    	
    	//Update relationship object
    	relObjectToUpdate = new CIRelationship(namespace, 
    			fromObject, toObject, relType, status);
    	relObjectToUpdate.addAttribute(new CIRelationshipAttribute(
    			relAttribute2, "West"));
    	
    	//PUT the relationship without expecting any response
    	r.path(relationsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.put(relObjectToUpdate);
    	
    	//Get the relationship
    	selected = r.path(
    			relationURI + fromObject + "~" + relType + "~" + toObject)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.get(CIRelationship.class);
    	Assert.assertNotNull(selected);
    	Assert.assertEquals(1, selected.getAttributes().size());
    	print(selected);
    	
    	//Update relationship object
    	relObjectToUpdate = new CIRelationship(namespace, 
    			fromObject, toObject, relType, status);

    	//PUT the relationship without expecting any response
    	r.path(relationsURI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.put(relObjectToUpdate);
    	
    	//Get the relationship
    	selected = r.path(
    			relationURI + fromObject + "~" + relType + "~" + toObject)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.get(CIRelationship.class);
    	Assert.assertNotNull(selected);
    	Assert.assertNull(selected.getAttributes());
    	print(selected);
    	
    	//Delete the objects
    	r.path(objectURI + fromObject).delete();
    	r.path(objectURI + toObject).delete();
    	
    	//Delete meta class relationships
    	r.path(metaRelationshipsURI + fromType + "/" + relType + "/" + toType)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.delete();
    	
    	//Delete the types
    	r.path(typesURI + fromType).delete();
    	r.path(typesURI + toType).delete();
    	r.path(typesURI + relType).delete();
    	
    	//Delete the status
    	r.path(statusesURI + status).delete();
    }

	private void print(CIObject object) {
		System.out.printf("%s %s %s %s\n", object.getName(), 
				object.getNamespace(), object.getStatus(), object.getType());
		if (object.getAttributes() != null) {
			for (CIAttribute attribute : object.getAttributes()) {
				print(attribute);
			}
		}
	}
	
	private void print(CIAttribute attribute) {
		System.out.printf("\t%s:%s\n", attribute.getName(), 
				attribute.getValue());
	}
	
	private void print(CIRelationship relation) {
		System.out.printf("%s %s %s %s\n", relation.getName(), 
				relation.getNamespace(), relation.getStatus(), 
				relation.getType());
		if (relation.getAttributes() != null) {
			for (CIRelationshipAttribute attribute : relation.getAttributes()) {
				print(attribute);
			}
		}
	}
	
	private void print(CIRelationshipAttribute attribute) {
		System.out.printf("\t%s:%s\n", attribute.getName(), 
				attribute.getValue());
	}
	
}
