package com.palm.cloud.services.cmdb.resource.collection;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.collection.Node;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

@Path("/data/collection/{collectionName}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class CollectionResource extends AbstractBaseResource {

	@GET
	public List<Node> getCollection(
			@PathParam("collectionName") String collectionName,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return collectionService.getCollection(collectionName, 
				offset, maxResults);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable getCollectionAsHtml(
			@PathParam("collectionName") String collectionName,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return new Viewable("/view/collection/nodes.jsp", 
				this.getCollection(collectionName, offset, maxResults));
	}
	
	@GET
	@Path("{root}")
	public Node getCollectionByRoot(
			@PathParam("collectionName") String collectionName,
			@PathParam("root") String root) {
		
		return collectionService.getCollectionByRoot(collectionName, root);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{root}")
	public Viewable getCollectionByRootAsHtml(
			@PathParam("collectionName") String collectionName,
			@PathParam("root") String root) {
		
		return new Viewable("/view/collection/nodes.jsp", 
				Arrays.asList(new Node[] {this
						.getCollectionByRoot(collectionName, root)}));
	}
	
}
