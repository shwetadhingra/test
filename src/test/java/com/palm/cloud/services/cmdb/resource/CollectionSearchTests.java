package com.palm.cloud.services.cmdb.resource;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.palm.cloud.services.cmdb.collection.Link;
import com.palm.cloud.services.cmdb.collection.Node;
import com.palm.cloud.services.cmdb.collection.xml.Edge;
import com.palm.cloud.services.cmdb.collection.xml.Vertex;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class CollectionSearchTests {

	private static final String BASE_URL = "http://localhost:8080/oocmdb/rs/";
	
	private static final String COLLECTION_SEARCH_URI = "data/search/collection";
	
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
	public void testSearchByInlineCollection() {
		Vertex rack = new Vertex();
		rack.setType("Rack");
		Edge containedIn = new Edge();
		containedIn.setType("ContainedIn");
		containedIn.setDirection("reverse");
		Vertex physicalServer = new Vertex();
		physicalServer.setType("PhysicalServer");
		containedIn.setVertex(physicalServer);
		rack.setEdge(Arrays.asList(new Edge[] {containedIn} ));
		List<Node> results = r.path(COLLECTION_SEARCH_URI)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<List<Node>>() {}, rack);
		Assert.assertNotNull(results);
		for (Node node : results) {
			print(node);
		}
	}
	
	private void print(Node node) {
		print(node.getObject());
		if (node.getLinks() != null) {
			for (Link link : node.getLinks()) {
				print(link.getNode());
			}
		}
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
