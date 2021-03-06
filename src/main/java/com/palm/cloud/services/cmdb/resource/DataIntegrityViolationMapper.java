package com.palm.cloud.services.cmdb.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.Responses;

@Provider
@Component
public class DataIntegrityViolationMapper implements 
		ExceptionMapper<DataIntegrityViolationException> {

	public Response toResponse(DataIntegrityViolationException ex) {
		return Response.status(Responses.CLIENT_ERROR)
			.entity(ex.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}

}
