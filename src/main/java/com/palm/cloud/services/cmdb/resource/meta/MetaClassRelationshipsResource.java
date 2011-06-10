package com.palm.cloud.services.cmdb.resource.meta;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/meta/relationships")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaClassRelationshipsResource extends AbstractBaseResource {

	public MetaClassRelationshipsResource() {
		
	}
	
	@GET
	public List<MetaClassRelationship> getAllTypeRelationships() {
		return cmdbMetaService.getAllTypeRelationships();
	}
	
	@GET
	@Path("fromType")
	public List<MetaClassRelationship> getTypeRelationshipsByFromType(
			@QueryParam("fromType") String fromType) {
		
		return cmdbMetaService.getTypeRelationshipsByFromType(fromType);
	}
	
	@GET
	@Path("toType")
	public List<MetaClassRelationship> getTypeRelationshipsByToType(
			@QueryParam("toType") String toType) {
		
		return cmdbMetaService.getTypeRelationshipsByToType(toType);
	}
	
	@GET
	@Path("relationType")
	public List<MetaClassRelationship> getTypeRelationshipsByRelationType(
			@QueryParam("relationType") String relationType) {
		
		return cmdbMetaService.getTypeRelationshipsByRelationType(relationType);
	}
}
