package com.palm.cloud.services.cmdb.resource.meta;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaStatus;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.NotFoundException;

@Path("/meta/statuses/{status}")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaStatusResource extends AbstractBaseResource {

	public MetaStatusResource() {
		
	}
	
	@GET
	public MetaStatus getStatus(@PathParam("status") String status) {
		MetaStatus result = cmdbMetaService.getStatus(status);
		if (result == null)
			throw new NotFoundException("Status not found");
		return result;
	}
	
	@POST
	public void addStatus(@PathParam("status") String status) {
		cmdbMetaService.addStatus(status);
	}
	
	@DELETE
	public void deleteStatus(@PathParam("status") String status) {
		cmdbMetaService.deleteStatus(status);
	}

}
