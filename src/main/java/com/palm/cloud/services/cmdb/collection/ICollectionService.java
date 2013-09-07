package com.palm.cloud.services.cmdb.collection;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.palm.cloud.services.cmdb.collection.xml.Vertex;

@WebService
public interface ICollectionService {

	
	List<Node> getCollection(
			@WebParam(name = "collectionName") String collectionName, 
			@WebParam(name = "offset") int offset,
			@WebParam(name = "maxResults") int maxResults);
	
	Node getCollectionByRoot(
			@WebParam(name = "collectionName") String collectionName, 
			@WebParam(name = "root") String root);

	List<Node> getCollectionByXML(
			@WebParam(name = "definition") String definition, 
			@WebParam(name = "offset") int offset,
			@WebParam(name = "maxResults") int maxResults);
	
	List<Node> getCollectionByDefinition(
			@WebParam(name = "vertex") Vertex vertex, 
			@WebParam(name = "offset") int offset,
			@WebParam(name = "maxResults") int maxResults);
	
	void writeCollection(@WebParam(name="collection") List<Node> collection);
	
}
