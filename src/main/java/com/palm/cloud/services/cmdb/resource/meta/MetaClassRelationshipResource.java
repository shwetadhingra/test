package com.palm.cloud.services.cmdb.resource.meta;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/meta/relationships/{fromType}/{relationType}/{toType}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaClassRelationshipResource extends AbstractBaseResource {

	public MetaClassRelationshipResource() {
		
	}
	
	@POST
	public void addTypeRelationship(
			@PathParam("fromType") String fromType, 
			@PathParam("relationType") String relationType, 
			@PathParam("toType") String toType) {
		
		cmdbMetaService.addTypeRelationship(fromType, relationType, toType);
	}
	
	@DELETE
	public void deleteTypeRelationship(
			@PathParam("fromType") String fromType, 
			@PathParam("relationType") String relationType, 
			@PathParam("toType") String toType) {
		
		cmdbMetaService.deleteTypeRelationship(fromType, relationType, toType);
	}

}
