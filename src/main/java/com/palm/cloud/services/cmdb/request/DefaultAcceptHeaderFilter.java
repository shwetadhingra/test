package com.palm.cloud.services.cmdb.request;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class DefaultAcceptHeaderFilter implements ContainerRequestFilter {
	
	private static final String ACCEPT_HEADER = "accept";
	
	private static final String ACCEPT_ALL = "*/*";
	
	private static final String DEFAULT_ACCEPT_MEDIA_TYPE = "application/json";

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		String acceptHeader = request.getRequestHeaders()
				.getFirst(ACCEPT_HEADER);
		if (ACCEPT_ALL.equals(acceptHeader)) {
			request.getRequestHeaders().putSingle(ACCEPT_HEADER, 
					DEFAULT_ACCEPT_MEDIA_TYPE);
		}
		return request;
	}

}
