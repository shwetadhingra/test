package com.palm.cloud.services.cmdb.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.palm.cloud.services.cmdb.meta.MetaStatus;

@WebService
public interface ICMDBMetaService {

	void addStatus(@WebParam(name="status") String status);
	
	MetaStatus getStatus(@WebParam(name="status") String status);
	
	void deleteStatus(@WebParam(name="status") String status);
	
	List<MetaStatus> getAllStatuses();
	
	void addAttribute(@WebParam(name="attribute") String attribute);

	MetaAttribute getAttribute(@WebParam(name="attribute") String attribute);
	
	void deleteAttribute(@WebParam(name="attribute") String attribute);
	
	List<MetaAttribute> getAllAttributes();
	
	void addType(@WebParam(name="type") MetaClass type);
	
	void updateType(@WebParam(name="type") MetaClass type);
	
	void deleteType(@WebParam(name="type") String type);
	
	MetaClass getType(@WebParam(name="type") String type);
	
	List<MetaClass> getAllTypes();
	
	void addTypeRelationship(@WebParam(name="fromType") String fromType, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="toType") String toType);

	void deleteTypeRelationship(@WebParam(name="fromType") String fromType, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="toType") String toType);

	MetaClassRelationship getTypeRelationship(
			@WebParam(name="fromType") String fromType, 
			@WebParam(name="relationType") String relationType, 
			@WebParam(name="toType") String toType);

	List<MetaClassRelationship> getTypeRelationshipsByFromType(
			@WebParam(name="fromType") String fromType);

	List<MetaClassRelationship> getTypeRelationshipsByToType(
			@WebParam(name="toType") String toType);

	List<MetaClassRelationship> getTypeRelationshipsByRelationType(
			@WebParam(name="relationType") String relationType);

	List<MetaClassRelationship> getAllTypeRelationships();
	
	void addTypeAttribute(@WebParam(name="type") String type, 
			@WebParam(name="attribute") String attribute);

	void deleteTypeAttribute(@WebParam(name="type") String type, 
			@WebParam(name="attribute") String attribute);

}
