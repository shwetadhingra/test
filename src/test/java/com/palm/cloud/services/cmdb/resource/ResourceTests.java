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
	
}
