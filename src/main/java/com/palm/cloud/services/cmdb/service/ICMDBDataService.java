package com.palm.cloud.services.cmdb.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;

@WebService
public interface ICMDBDataService {

	void addObject(@WebParam(name="object") CIObject object);
	
	void updateObject(@WebParam(name="object") CIObject object);
	
	void deleteObject(@WebParam(name="name") String name);
	
	void deleteObjectNS(@WebParam(name="name") String name, 
			@WebParam(name="namespace") String namespace);
	
	CIObject getObject(@WebParam(name="name") String name);

	CIObject getObjectNS(@WebParam(name="name") String name, 
			@WebParam(name="namespace") String namespace);

	List<CIObject> getObjects(@WebParam(name="type") String type, 
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults);

	List<CIObject> getObjectsNS(@WebParam(name="type") String type, 
			@WebParam(name="namespace") String namespace,
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults);

	void addAttribute(@WebParam(name="objectName") String objectName, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);
	
	void addAttributeNS(@WebParam(name="objectName") String objectName,
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);
	
	void updateAttribute(@WebParam(name="objectName") String objectName, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);

	void updateAttributeNS(@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);

	void deleteAttribute(@WebParam(name="objectName") String objectName, 
			@WebParam(name="attributeName") String attributeName);
	
	void deleteAttributeNS(@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName);
	
	CIAttribute getAttribute(@WebParam(name="objectName") String objectName, 
			@WebParam(name="attributeName") String attributeName);
	
	CIAttribute getAttributeNS(@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName);
	
	List<CIAttribute> getAttributes(
			@WebParam(name="objectName") String objectName);

	List<CIAttribute> getAttributesNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace);

	void addRelation(@WebParam(name="relation") CIRelationship relation);
	
	void deleteRelation(@WebParam(name="name") String name);

	void deleteRelationNS(@WebParam(name="name") String name, 
			@WebParam(name="namespace") String namespace);

	CIRelationship getRelation(@WebParam(name="name") String name);
	
	CIRelationship getRelationNS(@WebParam(name="name") String name, 
			@WebParam(name="namespace") String namespace);

	List<CIRelationship> getRelations(
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults);
	
	List<CIRelationship> getRelationsNS(
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="namespace") String namespace,
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults);
	
	List<CIRelationship> getFromRelations(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType);

	List<CIRelationship> getFromRelationsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType);

	List<CIRelationship> getToRelations(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType);

	List<CIRelationship> getToRelationsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType);

	List<CIRelationship> getAllFromRelations(
			@WebParam(name="objectName") String objectName);

	List<CIRelationship> getAllFromRelationsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace);

	List<CIRelationship> getAllToRelations(
			@WebParam(name="objectName") String objectName);

	List<CIRelationship> getAllToRelationsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace);

	void addRelationAttribute(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);
	
	void addRelationAttributeNS(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);
	
	void updateRelationAttribute(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);

	void updateRelationAttributeNS(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName, 
			@WebParam(name="attributeValue") String attributeValue);

	void deleteRelationAttribute(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="attributeName") String attributeName);
	
	void deleteRelationAttributeNS(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName);
	
	CIRelationshipAttribute getRelationAttribute(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="attributeName") String attributeName);
	
	CIRelationshipAttribute getRelationAttributeNS(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="attributeName") String attributeName);
	
	List<CIRelationshipAttribute> getRelationAttributes(
			@WebParam(name="relationName") String relationName);

	List<CIRelationshipAttribute> getRelationAttributesNS(
			@WebParam(name="relationName") String relationName, 
			@WebParam(name="namespace") String namespace);

	List<CIObject> getFromObjectsByType(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type);
	
	List<CIObject> getFromObjectsByTypeNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type);
	
	List<CIObject> getToObjectsByType(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type);
	
	List<CIObject> getToObjectsByTypeNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type);
	
	List<CIObject> getFromObjectsByRelationType(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType);
	
	List<CIObject> getFromObjectsByRelationTypeNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType);
	
	List<CIObject> getToObjectsByRelationType(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType);
	
	List<CIObject> getToObjectsByRelationTypeNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType);
	
	List<CIObject> getFromObjects(
			@WebParam(name="objectName") String objectName);
	
	List<CIObject> getFromObjectsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace);
	
	List<CIObject> getToObjects(
			@WebParam(name="objectName") String objectName);
	
	List<CIObject> getToObjectsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace);
	
	List<CIObject> getObjectsByConditionsNS(@WebParam(name="type") String type,
			@WebParam(name="namespace") String namespace,
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults,
			@WebParam(name="conditions") Condition... conditions);

	List<CIObject> getObjectsByConditions(@WebParam(name="type") String type,
			@WebParam(name="offset") int offset, 
			@WebParam(name="maxResults") int maxResults,
			@WebParam(name="conditions") Condition... conditions);

	List<CIObject> getFromObjectsByConditions(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type,
			@WebParam(name="conditions") Condition... conditions);
	
	List<CIObject> getFromObjectsByConditionsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type,
			@WebParam(name="conditions") Condition... conditions);
	
	List<CIObject> getToObjectsByConditions(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type,
			@WebParam(name="conditions") Condition... conditions);
	
	List<CIObject> getToObjectsByConditionsNS(
			@WebParam(name="objectName") String objectName, 
			@WebParam(name="namespace") String namespace, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="type") String type,
			@WebParam(name="conditions") Condition... conditions);
	
}
