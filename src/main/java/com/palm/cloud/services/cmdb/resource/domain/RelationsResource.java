package com.palm.cloud.services.cmdb.resource.domain;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;

@Path("/data/relations")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class RelationsResource extends AbstractBaseResource {

	public RelationsResource() {

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRelation(CIRelationship relation) {
		cmdbDataService.addRelation(relation);
	}

	@Path("{type}")
	@GET
	public List<CIRelationship> getRelations(@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults) {

		return cmdbDataService.getRelations(type, offset, maxResults);
	}

}
