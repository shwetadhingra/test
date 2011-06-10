package com.palm.cloud.services.cmdb.resource.meta;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.NotFoundException;

@Path("/meta/attributes/{attribute}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaAttributeResource extends AbstractBaseResource {

	public MetaAttributeResource() {
		
	}
	
	@GET
	public MetaAttribute getAttribute(
			@PathParam("attribute") String attribute) {
		
		MetaAttribute result = cmdbMetaService.getAttribute(attribute);
		if (result == null)
			throw new NotFoundException("Attribute not found");
		return result;
	}
	
	@POST
	public void addAttribute(@PathParam("attribute") String attribute) {
		cmdbMetaService.addAttribute(attribute);
	}
	
	@DELETE
	public void deleteAttribute(@PathParam("attribute") String attribute) {
		cmdbMetaService.deleteAttribute(attribute);
	}

}
