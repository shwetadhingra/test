package com.palm.cloud.services.cmdb.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class PerformanceTests {

	private static final String BASE_URL = "http://10.125.0.216:8080/oocmdb/rs/";

	private static final String OBJECTS_URI = "data/objects";
	
	private static final String OBJECT_URI = "data/object/";
	
	private static final String RELATIONSHIPS_URI = "data/relations";
	
	private static final String RELATIONSHIP_URI = "data/relation/";
	
	private static final String PREFIX = "PERF";
	
	private static final int LOCATION_COUNT = 5;

	private static final int DC_COUNT = 5;

	private static final int CAGE_COUNT = 10;

	private static final int RACK_COUNT = 20;

	private static final int SERVER_COUNT = 40;

	private static WebResource r;
	
	private boolean print = false;
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	@BeforeClass
	public static void initialize() {
    	ClientConfig clientConfig = new DefaultClientConfig();
    	clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, 
    			Boolean.TRUE);
    	Client client = Client.create(clientConfig);
    	r = client.resource(BASE_URL);
	}
	
    @Test
    @Ignore
    public void testPerformance() {
    	loadObjects();
    	deleteObjects();
    }
    
    private void loadObjects() {
    	long startTime = System.currentTimeMillis();
    	int dcIndex = 0;
    	int cageIndex = 0;
    	int rackIndex = 0;
    	int serverIndex = 0;
    	int totalObjects = 0;
    	for (int i = 0; i < LOCATION_COUNT; i++) {
        	String location = PREFIX + "Location" + i;
        	addLocation(location);
        	totalObjects++;
        	CIObject objectAdded = getObject(location);
        	Assert.assertNotNull(objectAdded);
        	print(objectAdded);
        	for (int j = 0; j < DC_COUNT; j++) {
            	String dc = PREFIX + "DC" + dcIndex;
            	addDatacenter(dc);
            	totalObjects++;
            	objectAdded = getObject(dc);
            	Assert.assertNotNull(objectAdded);
            	print(objectAdded);
            	addRelation(dc, location, "LocatedIn");
            	dcIndex++;
            	for (int k = 0; k < CAGE_COUNT; k++) {
                	String cage = PREFIX + "Cage" + cageIndex;
                	addCage(cage);
                	totalObjects++;
                	objectAdded = getObject(cage);
                	Assert.assertNotNull(objectAdded);
                	print(objectAdded);
                	addRelation(cage, dc, "LocatedIn");
                	cageIndex++;
                	for (int l = 0; l < RACK_COUNT; l++) {
                    	String rack = PREFIX + "Rack" + rackIndex;
                    	addRack(rack);
                    	totalObjects++;
                    	objectAdded = getObject(rack);
                    	Assert.assertNotNull(objectAdded);
                    	print(objectAdded);
                    	addRelation(rack, cage, "ContainedIn");
                    	rackIndex++;
                    	for (int m = 0; m < SERVER_COUNT; m++) {
                        	String server = PREFIX + "Server" + serverIndex;
                        	addPhysicalServer(server);
                        	totalObjects++;
                        	objectAdded = getObject(server);
                        	Assert.assertNotNull(objectAdded);
                        	print(objectAdded);
                        	addRelation(server, rack, "ContainedIn");
                        	serverIndex++;
                    	}
                	}
            	}
        	}
    	}
		System.out.println("Time taken to load "
				+ totalObjects
				+ " objects: "
				+ (System.currentTimeMillis() - startTime)
				+ " ms");
    }
    
    private void deleteObjects() {
    	long startTime = System.currentTimeMillis();
    	int dcIndex = 0;
    	int cageIndex = 0;
    	int rackIndex = 0;
    	int serverIndex = 0;
    	int totalObjects = 0;
    	for (int i = 0; i < LOCATION_COUNT; i++) {
        	String location = PREFIX + "Location" + i;
        	deleteObject(location);
        	totalObjects++;
        	for (int j = 0; j < DC_COUNT; j++) {
            	String dc = PREFIX + "DC" + dcIndex;
            	deleteObject(dc);
            	totalObjects++;
            	dcIndex++;
            	for (int k = 0; k < CAGE_COUNT; k++) {
                	String cage = PREFIX + "Cage" + cageIndex;
                	deleteObject(cage);
                	totalObjects++;
                	cageIndex++;
                	for (int l = 0; l < RACK_COUNT; l++) {
                    	String rack = PREFIX + "Rack" + rackIndex;
                    	deleteObject(rack);
                    	totalObjects++;
                    	rackIndex++;
                    	for (int m = 0; m < SERVER_COUNT; m++) {
                        	String server = PREFIX + "Server" + serverIndex;
                        	deleteObject(server);
                        	totalObjects++;
                        	serverIndex++;
                    	}
                	}
            	}
        	}
    	}
		System.out.println("Time taken to delete "
				+ totalObjects
				+ " objects: "
				+ (System.currentTimeMillis() - startTime)
				+ " ms");
    }
    
    private void addPhysicalServer(String name) {
    	String namespace = "DEFAULT";
    	String type = "PhysicalServer";
    	String status = "live";
    	Map<String, String> attributes = new HashMap<String, String>();
    	attributes.put("serialnumber", name);
    	attributes.put("assettag", name);
    	addObject(name, namespace, type, status, attributes);
    }
    
    private void addRack(String name) {
    	String namespace = "DEFAULT";
    	String type = "Rack";
    	String status = "live";
    	Map<String, String> attributes = new HashMap<String, String>();
    	attributes.put("name", name);
    	attributes.put("capacity_u", name);
    	addObject(name, namespace, type, status, attributes);
    }
    
    private void addCage(String name) {
    	String namespace = "DEFAULT";
    	String type = "Cage";
    	String status = "live";
    	Map<String, String> attributes = new HashMap<String, String>();
    	attributes.put("name", name);
    	attributes.put("shortname", name);
    	addObject(name, namespace, type, status, attributes);
    }
    
    private void addDatacenter(String name) {
    	String namespace = "DEFAULT";
    	String type = "Datacenter";
    	String status = "live";
    	Map<String, String> attributes = new HashMap<String, String>();
    	attributes.put("name", name);
    	attributes.put("shortname", name);
    	addObject(name, namespace, type, status, attributes);
    }
    
    private void addLocation(String name) {
    	String namespace = "DEFAULT";
    	String type = "Location";
    	String status = "live";
    	Map<String, String> attributes = new HashMap<String, String>();
    	attributes.put("name", name);
    	attributes.put("state", name);
    	attributes.put("country", name);
    	addObject(name, namespace, type, status, attributes);
    }
    
    private void addObject(String name, String namespace, String type, 
    		String status, Map<String, String> attributes) {
    	
    	//Add object
    	CIObject objectToAdd = new CIObject(name, namespace, type, status);
    	
    	if (attributes != null) {
    		for (String attributeName : attributes.keySet()) {
    			CIAttribute attribute = new CIAttribute(attributeName, 
    					attributes.get(attributeName));
    			objectToAdd.addAttribute(attribute);
    		}
    	}
    	
    	//Post the object without expecting any response
    	r.path(OBJECTS_URI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(objectToAdd);
    }
    
    private CIObject getObject(String name) {
    	//Retrieve the object
    	return r.path(OBJECT_URI + name)
			.accept(MediaType.APPLICATION_JSON)
			.type(MediaType.APPLICATION_JSON)
			.get(CIObject.class);
    	
    }

    private void deleteObject(String name) {
    	//Delete the object
    	r.path(OBJECT_URI + name).delete();
    }
    
    private void addRelation(String fromObject, String toObject, String type) {
    	
    	String namespace = "DEFAULT";
    	String status = "live";

    	//Add relation
    	CIRelationship relation = new CIRelationship(namespace, fromObject, 
    			toObject, type, status);
    	
    	//Post the object without expecting any response
    	r.path(RELATIONSHIPS_URI)
    		.accept(MediaType.APPLICATION_JSON)
    		.type(MediaType.APPLICATION_JSON)
    		.post(relation);
    }
    
    @SuppressWarnings("unused")
	private void deleteRelation(String fromObject, String toObject, 
    		String type) {
    	
    	//Delete the relation
    	r.path(RELATIONSHIP_URI + fromObject + "~" + type + "~" + toObject)
    		.delete();
    }
    
	private void print(CIObject object) {
		if (isPrint()) {
			System.out.printf("%s %s %s %s\n", object.getName(), 
					object.getNamespace(), object.getStatus(), object.getType());
			if (object.getAttributes() != null) {
				for (CIAttribute attribute : object.getAttributes()) {
					print(attribute);
				}
			}
		}
	}
	
	private void print(CIAttribute attribute) {
		System.out.printf("\t%s:%s\n", attribute.getName(), 
				attribute.getValue());
	}
	
}
