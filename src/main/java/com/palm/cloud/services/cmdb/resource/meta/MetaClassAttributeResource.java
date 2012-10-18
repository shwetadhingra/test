package com.palm.cloud.services.cmdb.resource.meta;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/meta/types/{type}/attributes/{attribute}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaClassAttributeResource extends AbstractBaseResource {

	@POST
	public void addAttribute(@PathParam("type") String type, 
			@PathParam("attribute") String attribute) {
		
		cmdbMetaService.addTypeAttribute(type, attribute);
	}
	
	@DELETE
	public void deleteAttribute(@PathParam("type") String type, 
			@PathParam("attribute") String attribute) {
		
		cmdbMetaService.deleteTypeAttribute(type, attribute);
	}

}
