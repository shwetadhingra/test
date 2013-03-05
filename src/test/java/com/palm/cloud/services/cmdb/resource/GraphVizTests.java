package com.palm.cloud.services.cmdb.resource;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class GraphVizTests {

	private static final String BASE_URL = "http://localhost:8080/oocmdb/rs/";
	
	private static final String TYPES_URI = "meta/types";
	
	private static final String RELATIONSHIPS_URI = "meta/relationships";
	
	private static WebResource r;
	
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
	public void testBuildMetaTypeRelationshipsGraph() {
		System.out.println("digraph TypeRelationships {");
		System.out.println();
		List<MetaClass> types = r.path(TYPES_URI)
				.accept(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<MetaClass>>() {});
		if (types != null) {
			for (MetaClass type : types) {
				if (!type.isRelationshipType()) {
					System.out.println("\t" 
							+ type.getName()
							+ "[label = \""
							+ type.getName()
							+ "\"];");
				}
			}
		}
		System.out.println();
		List<MetaClassRelationship> relationships = r.path(RELATIONSHIPS_URI)
				.accept(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<MetaClassRelationship>>() {});
		if (relationships != null) {
			for (MetaClassRelationship relationship : relationships) {
				System.out.println("\t" 
						+ relationship.getFromType().getName() 
						+ " -> "
						+ relationship.getToType().getName()
						+ "[label = \""
						+ relationship.getRelationshipType().getName()
						+ "\"];");
			}
		}
		System.out.println();
		System.out.println("}");
	}

}
