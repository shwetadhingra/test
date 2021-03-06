package com.palm.cloud.services.cmdb.resource.domain;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

@Path("/data/objects")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ObjectsResource extends AbstractBaseResource {

	public ObjectsResource() {
		
	}
	
	@Path("type/{type}")
	@GET
	public List<CIObject> getObjects(@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return cmdbDataService.getObjects(type, offset, maxResults);
	}

	@Path("type/{type}")
	@Produces(MediaType.TEXT_HTML)
	@GET
	public Viewable getObjectsAsHtml(@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return new Viewable("/view/object/objects.jsp", this.getObjects(type, 
				offset, maxResults));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addObject(CIObject object) {
		cmdbDataService.addObject(object);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateObject(CIObject object) {
		cmdbDataService.updateObject(object);
	}
	
	@Path("type/{type}/{objectNameLike}")
	@GET
	public List<CIObject> getObjectsByNameAndType(
			@PathParam("objectNameLike") String objectNameLike,
			@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return cmdbDataService.getObjectsByNameAndType(objectNameLike, type, 
				offset, maxResults);
	}
	
	@Path("type/{type}/{objectNameLike}")
	@Produces(MediaType.TEXT_HTML)
	@GET
	public Viewable getObjectsByNameAndTypeAsHtml(
			@PathParam("objectNameLike") String objectNameLike,
			@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return new Viewable("/view/object/objects.jsp", 
				this.getObjectsByNameAndType(
						objectNameLike, type, offset, maxResults));
	}
	
	@Path("name/{objectNameLike}")
	@GET
	public List<CIObject> getObjectsByName(
			@PathParam("objectNameLike") String objectNameLike,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return cmdbDataService.getObjectsByName(
				objectNameLike, offset, maxResults);
	}
	
	@Path("name/{objectNameLike}")
	@Produces(MediaType.TEXT_HTML)
	@GET
	public Viewable getObjectsByNameAsHtml(
			@PathParam("objectNameLike") String objectNameLike,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {
		
		return new Viewable("/view/object/objects.jsp", 
				this.getObjectsByName(objectNameLike, offset, maxResults));
	}
	
}
