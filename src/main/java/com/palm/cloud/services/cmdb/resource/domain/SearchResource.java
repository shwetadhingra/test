package com.palm.cloud.services.cmdb.resource.domain;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.palm.cloud.services.cmdb.collection.xml.XMLParser;
import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.resource.AbstractBaseResource;
import com.sun.jersey.api.view.Viewable;

@Path("/data/search")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class SearchResource extends AbstractBaseResource {

	public SearchResource() {
		
	}
	
	@Path("type/{type}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CIObject> getObjectsByConditions(
			@PathParam("type") String type,
			@QueryParam("offset") @DefaultValue("0") int offset,
			@QueryParam("maxResults") @DefaultValue("100") int maxResults,
			Condition... conditions) {
		
		return cmdbDataService.getObjectsByConditions(type, offset, maxResults, 
				conditions);
	}
	
	@Path("type/{type}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public Viewable getObjectsByConditionsAsHtml(
			@FormParam("type") String type,
			@FormParam("offset") @DefaultValue("0") int offset,
			@FormParam("maxResults") @DefaultValue("100") int maxResults,
			@FormParam("conditions") String conditions) {
		
		List<CIObject> objects = null;
		try {
			Condition condition = XMLParser.unmarshall(Condition.class, 
					conditions);
			objects = cmdbDataService.getObjectsByConditions(
					type, offset, maxResults, condition);
			
		} catch (Exception e) {
		}
		return new Viewable("/view/object/objects.jsp", objects);
	}
	
}
