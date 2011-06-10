package com.palm.cloud.services.cmdb.resource.meta;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/meta/types")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaClassesResource extends AbstractBaseResource {
	
	public MetaClassesResource() {
		
	}
	
	@GET
	public List<MetaClass> getTypes() {
		return cmdbMetaService.getAllTypes();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addType(MetaClass type) {
		cmdbMetaService.addType(type);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateType(MetaClass type) {
		cmdbMetaService.updateType(type);
	}

}
