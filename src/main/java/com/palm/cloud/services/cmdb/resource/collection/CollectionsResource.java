package com.palm.cloud.services.cmdb.resource.collection;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.collection.Node;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/data/collections")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class CollectionsResource extends AbstractBaseResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void writeCollection(List<Node> collection) {
		collectionService.writeCollection(collection);
	}
	
}
