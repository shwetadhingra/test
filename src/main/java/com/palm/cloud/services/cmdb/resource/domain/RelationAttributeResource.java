package com.palm.cloud.services.cmdb.resource.domain;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/data/relation/{relationName}/attributes/{attributeName}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class RelationAttributeResource extends AbstractBaseResource {

	public RelationAttributeResource() {
		
	}

	@GET
	public CIRelationshipAttribute getRelationAttribute(
			@PathParam("relationName") String relationName, 
			@PathParam("attributeName") String attributeName) {
		
		return cmdbDataService.getRelationAttribute(relationName, 
				attributeName);
	}

	@POST
	public void addRelationAttribute(
			@PathParam("relationName") String relationName, 
			@PathParam("attributeName") String attributeName,
			@QueryParam("attributeValue") String attributeValue) {
		
		cmdbDataService.addRelationAttribute(relationName, attributeName, 
				attributeValue);
	}
	
	@PUT
	public void updateRelationAttribute(
			@PathParam("relationName") String relationName, 
			@PathParam("attributeName") String attributeName,
			@QueryParam("attributeValue") String attributeValue) {
		
		cmdbDataService.updateRelationAttribute(relationName, attributeName, 
				attributeValue);
	}
	
	@DELETE
	public void deleteRelationAttribute(
			@PathParam("relationName") String relationName, 
			@PathParam("attributeName") String attributeName) {
		
		cmdbDataService.deleteRelationAttribute(relationName, attributeName);
	}

}
