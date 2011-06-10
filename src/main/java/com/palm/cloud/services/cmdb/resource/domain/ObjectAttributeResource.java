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

import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/data/object/{objectName}/attributes/{attributeName}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class ObjectAttributeResource extends AbstractBaseResource {

	public ObjectAttributeResource() {
		
	}
	
	@GET
	public CIAttribute getAttribute(@PathParam("objectName") String objectName,
			@PathParam("attributeName") String attributeName) {
		
		return cmdbDataService.getAttribute(objectName, attributeName);
	}

	@POST
	public void addAttribute(@PathParam("objectName") String objectName,
			@PathParam("attributeName") String attributeName,
			@QueryParam("attributeValue") String attributeValue) {
		
		cmdbDataService.addAttribute(objectName, attributeName, attributeValue);
	}
	
	@PUT
	public void updateAttribute(@PathParam("objectName") String objectName,
			@PathParam("attributeName") String attributeName,
			@QueryParam("attributeValue") String attributeValue) {
		
		cmdbDataService.updateAttribute(objectName, attributeName, 
				attributeValue);
	}
	
	@DELETE
	public void deleteAttribute(@PathParam("objectName") String objectName,
			@PathParam("attributeName") String attributeName) {
		
		cmdbDataService.deleteAttribute(objectName, attributeName);
	}

}
