package com.palm.cloud.services.cmdb.resource.meta;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.NotFoundException;

@Path("/meta/types/{type}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaClassResource extends AbstractBaseResource {

	public MetaClassResource() {
		
	}
	
	@GET
	public MetaClass getType(@PathParam("type") String type) {
		
		MetaClass result = cmdbMetaService.getType(type);
		if (result == null)
			throw new NotFoundException("Type not found");
		return result;
	}
	
	@DELETE
	public void deleteType(@PathParam("type") String type) {
		cmdbMetaService.deleteType(type);
	}

}
