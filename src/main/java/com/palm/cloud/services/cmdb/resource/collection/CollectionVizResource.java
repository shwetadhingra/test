package com.palm.cloud.services.cmdb.resource.collection;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.collection.Link;
import com.palm.cloud.services.cmdb.collection.Node;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

@Path("/data/collectionviz/{collectionName}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class CollectionVizResource extends AbstractBaseResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable getCollectionVizAsHtml(
			@PathParam("collectionName") String collectionName,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		List<Node> nodes = collectionService.getCollection(collectionName, 
				offset, maxResults);
		if (nodes != null) {
			Set<String> vertices = new HashSet<String>();
			Set<Map.Entry<String, Map.Entry<String, String>>> edges 
				= new HashSet<Map.Entry<String, Map.Entry<String, String>>>();
			for (Node node : nodes) {
				vertices = getVertices(node, vertices);
				edges = getEdges(node, edges);
			}
			model.put("vertices", vertices);
			model.put("edges", edges);
		}
		return new Viewable("/view/collection/nodesviz.jsp", model);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{root}")
	public Viewable getCollectionVizByRootAsHtml(
			@PathParam("collectionName") String collectionName,
			@PathParam("root") String root) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		Node node = collectionService
				.getCollectionByRoot(collectionName, root);
		if (node != null) {
			Set<String> vertices = new HashSet<String>();
			Set<Map.Entry<String, Map.Entry<String, String>>> edges 
				= new HashSet<Map.Entry<String, Map.Entry<String, String>>>();
			vertices = getVertices(node, vertices);
			edges = getEdges(node, edges);
			model.put("vertices", vertices);
			model.put("edges", edges);
		}
		
		return new Viewable("/view/collection/nodesviz.jsp", model);
	}
	
	private Set<String> getVertices(Node node, Set<String> vertices) {
		if (vertices == null) {
			vertices = new HashSet<String>();
		}
		vertices.add(node.getObject().getName());
		if (node.getLinks() != null) {
			for (Link link : node.getLinks()) {
				vertices = getVertices(link.getNode(), vertices);
			}
		}
		return vertices;
	}

	private Set<Map.Entry<String, Map.Entry<String, String>>> getEdges(
			Node node, 
			Set<Map.Entry<String, Map.Entry<String, String>>> edges) {
		
		if (edges == null) {
			edges = new HashSet<Map.Entry<String, Map.Entry<String, String>>>();
		}
		String objectName = node.getObject().getName();
		if (node.getLinks() != null) {
			for (Link link : node.getLinks()) {
				Node toNode = link.getNode();
				String toObjectName = toNode.getObject().getName();
				edges = getEdges(toNode, edges);
				if (link.isForward()) {
					edges.add(new AbstractMap.SimpleImmutableEntry<String, 
							Map.Entry<String, String>>(objectName, 
									new AbstractMap
										.SimpleImmutableEntry<String, String>(
												link.getType(), toObjectName)));
				} else {
					edges.add(new AbstractMap.SimpleImmutableEntry<String, 
							Map.Entry<String, String>>(toObjectName, 
									new AbstractMap
										.SimpleImmutableEntry<String, String>(
												link.getType(), objectName)));
				}
			}
		}
		return edges;
	}

}
