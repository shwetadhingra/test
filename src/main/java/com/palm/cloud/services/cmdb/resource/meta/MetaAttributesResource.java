package com.palm.cloud.services.cmdb.resource.meta;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/meta/attributes")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MetaAttributesResource extends AbstractBaseResource {

	public MetaAttributesResource() {
		
	}
	
	@GET
	public List<MetaAttribute> getAttributes() {
		return cmdbMetaService.getAllAttributes();
	}

}
