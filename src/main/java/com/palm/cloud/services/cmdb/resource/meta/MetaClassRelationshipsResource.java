package com.palm.cloud.services.cmdb.resource.meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

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
	
	@Produces(MediaType.TEXT_HTML)
	@GET
	public Viewable getAllTypeRelationshipsAsHtml() {
		return new Viewable("/view/meta/relationships.jsp", 
				this.getAllTypeRelationships());
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

	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("viz")
	public Viewable getTypeRelationshipsVizAsHtml() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("types", cmdbMetaService.getAllTypes());
		model.put("relationships", this.getAllTypeRelationships());
		return new Viewable("/view/meta/relationshipsviz.jsp", model);
	}

}
