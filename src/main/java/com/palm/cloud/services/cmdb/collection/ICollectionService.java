package com.palm.cloud.services.cmdb.collection;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ICollectionService {

	
	List<Node> getCollection(
			@WebParam(name = "collectionName") String collectionName, 
			@WebParam(name = "offset") int offset,
			@WebParam(name = "maxResults") int maxResults);
	
	Node getCollectionByRoot(
			@WebParam(name = "collectionName") String collectionName, 
			@WebParam(name = "root") String root);

}
