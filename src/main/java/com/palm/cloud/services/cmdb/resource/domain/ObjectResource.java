package com.palm.cloud.services.cmdb.resource.domain;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

@Path("/data/object/{name}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ObjectResource extends AbstractBaseResource {

	public ObjectResource() {
		
	}
	
	@GET
	public CIObject getObject(@PathParam("name") String name) {
		return cmdbDataService.getObject(name);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable getObjectAsHtml(@PathParam("name") String name) {
		return new Viewable("/view/object/object.jsp", this.getObject(name));
	}
	
	@DELETE
	public void deleteObject(@PathParam("name") String name) {
		cmdbDataService.deleteObject(name);
	}

	@GET
	@Path("attributes")
	public List<CIAttribute> getAttributes(
			@PathParam("name") String objectName) {
		
		return cmdbDataService.getAttributes(objectName);
	}
	
	@GET
	@Path("from-rels")
	public List<CIRelationship> getAllFromRelations(
			@PathParam("name") String objectName) {
		
		return cmdbDataService.getAllFromRelations(objectName);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("from-rels")
	public Viewable getAllFromRelationsAsHtml(
			@PathParam("name") String objectName) {
		
		return new Viewable("/view/relation/relations.jsp", 
				this.getAllFromRelations(objectName));
	}

	@GET
	@Path("to-rels")
	public List<CIRelationship> getAllToRelations(
			@PathParam("name") String objectName) {
		
		return cmdbDataService.getAllToRelations(objectName);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("to-rels")
	public Viewable getAllToRelationsAsHtml(
			@PathParam("name") String objectName) {
		
		return new Viewable("/view/relation/relations.jsp", 
				this.getAllToRelations(objectName));
	}

	@GET
	@Path("from-rels/{relationType}")
	public List<CIRelationship> getFromRelations(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType) {
		
		return cmdbDataService.getFromRelations(objectName, relationType);
	}
	
	@GET
	@Path("to-rels/{relationType}")
	public List<CIRelationship> getToRelations(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType) {
		
		return cmdbDataService.getToRelations(objectName, relationType);
	}

	@GET
	@Path("froms")
	public List<CIObject> getFromObjects(@PathParam("name") String objectName) {
		return cmdbDataService.getFromObjects(objectName);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("froms")
	public Viewable getFromObjectsAsHtml(@PathParam("name") String objectName) {
		return new Viewable("/view/object/objects.jsp", 
				this.getFromObjects(objectName));
	}
	
	@GET
	@Path("tos")
	public List<CIObject> getToObjects(@PathParam("name") String objectName) {
		return cmdbDataService.getToObjects(objectName);
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("tos")
	public Viewable getToObjectsAsHtml(@PathParam("name") String objectName) {
		return new Viewable("/view/object/objects.jsp", 
				this.getToObjects(objectName));
	}
	
	@GET
	@Path("froms/{relationType}")
	public List<CIObject> getFromObjectsByRelationType(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType) {
		
		return cmdbDataService.getFromObjectsByRelationType(objectName, 
				relationType);
	}
	
	@GET
	@Path("tos/{relationType}")
	public List<CIObject> getToObjectsByRelationType(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType) {
		
		return cmdbDataService.getToObjectsByRelationType(objectName, 
				relationType);
	}
	
	@GET
	@Path("froms/{relationType}/{type}")
	public List<CIObject> getFromObjectsByType(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType, 
			@PathParam("type") String type) {
		
		return cmdbDataService.getFromObjectsByType(objectName, relationType, 
				type);
	}
	
	@GET
	@Path("tos/{relationType}/{type}")
	public List<CIObject> getToObjectsByType(
			@PathParam("name") String objectName, 
			@PathParam("relationType") String relationType, 
			@PathParam("type") String type) {
		
		return cmdbDataService.getToObjectsByType(objectName, relationType, 
				type);
	}
	
}
