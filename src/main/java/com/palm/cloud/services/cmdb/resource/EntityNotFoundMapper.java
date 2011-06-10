package com.palm.cloud.services.cmdb.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.Responses;

@Provider
@Component
public class EntityNotFoundMapper implements 
		ExceptionMapper<EmptyResultDataAccessException> {

	public Response toResponse(EmptyResultDataAccessException ex) {
		return Response.status(Responses.NOT_FOUND)
			.entity(ex.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}

}
