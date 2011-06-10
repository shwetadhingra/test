package com.palm.cloud.services.cmdb.resource.domain;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/data/relation/{name}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class RelationResource extends AbstractBaseResource {

	public RelationResource() {
		
	}
	
	@GET
	public CIRelationship getRelation(@PathParam("name") String name) {
		return cmdbDataService.getRelation(name);
	}
	
	@DELETE
	public void deleteRelation(@PathParam("name") String name) {
		cmdbDataService.deleteRelation(name);
	}

	@GET
	@Path("attributes")
	public List<CIRelationshipAttribute> getRelationAttributes(
			@PathParam("name") String relationName) {
		
		return cmdbDataService.getRelationAttributes(relationName);
	}

}
