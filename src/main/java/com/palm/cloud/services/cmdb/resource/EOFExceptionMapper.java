package com.palm.cloud.services.cmdb.resource;

import java.io.EOFException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.sun.jersey.api.Responses;

@Provider
@Component
public class EOFExceptionMapper implements ExceptionMapper<EOFException> {

	public Response toResponse(EOFException ex) {
		return Response.status(Responses.CLIENT_ERROR)
			.entity(ex.getMessage())
			.type(MediaType.TEXT_PLAIN)
			.build();
	}

}
