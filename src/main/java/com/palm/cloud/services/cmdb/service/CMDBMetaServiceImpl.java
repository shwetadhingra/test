package com.palm.cloud.services.cmdb.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaClassAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaClassDO;
import com.palm.cloud.services.cmdb.entity.MetaClassRelationshipDO;
import com.palm.cloud.services.cmdb.entity.MetaStatusDO;
import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;
import com.palm.cloud.services.cmdb.meta.MetaStatus;

@Transactional
@WebService
public class CMDBMetaServiceImpl extends AbstractBaseServiceImpl 
		implements ICMDBMetaService {

	public void addStatus(String status) {
		MetaStatusDO entity = new MetaStatusDO(status);
		entity = metaStatusDAO.create(entity);
	}

	public MetaStatus getStatus(String status) {
		MetaStatusDO entity = metaStatusDAO.findByName(status);
		return new MetaStatus(entity.getName());
	}

	public void deleteStatus(String status) {
		MetaStatusDO entity = metaStatusDAO.findByName(status);
		metaStatusDAO.delete(entity);
	}

	public List<MetaStatus> getAllStatuses() {
		List<MetaStatus> statuses = new ArrayList<MetaStatus>();
		List<MetaStatusDO> entities = metaStatusDAO.findAll();
		for (MetaStatusDO entity : entities) {
			MetaStatus status = new MetaStatus(entity.getName());
			statuses.add(status);
		}
		return statuses;
	}

	public void addAttribute(String attribute) {
		MetaAttributeDO entity = new MetaAttributeDO(attribute);
		entity = metaAttributeDAO.create(entity);
	}

	public MetaAttribute getAttribute(String attribute) {
		MetaAttributeDO entity = metaAttributeDAO.findByName(attribute);
		return new MetaAttribute(entity.getName());
	}
	
	public void deleteAttribute(String attribute) {
		MetaAttributeDO entity = metaAttributeDAO.findByName(attribute);
		metaAttributeDAO.delete(entity);
	}

	public List<MetaAttribute> getAllAttributes() {
		List<MetaAttribute> attributes = new ArrayList<MetaAttribute>();
		List<MetaAttributeDO> entities = metaAttributeDAO.findAll();
		for (MetaAttributeDO entity : entities) {
			attributes.add(new MetaAttribute(entity.getName()));
		}
		return attributes;
	}

	public void addType(MetaClass type) {
		MetaClassDO entity = toData(type);
		entity = metaClassDAO.create(entity);
	}

	public void updateType(MetaClass type) {
		MetaClassDO entity = metaClassDAO.findByName(type.getName());
		if (entity != null) {
			entity = toData(type);
			entity = metaClassDAO.update(entity);
		}
	}

	public void deleteType(String type) {
		MetaClassDO entity = metaClassDAO.findByName(type);
		metaClassDAO.delete(entity);
	}

	public MetaClass getType(String type) {
		MetaClassDO entity = metaClassDAO.findByName(type);
		return toDomain(entity);
	}

	public List<MetaClass> getAllTypes() {
		List<MetaClass> types = new ArrayList<MetaClass>();
		List<MetaClassDO> entities = metaClassDAO.findAll();
		for (MetaClassDO entity : entities) {
			types.add(toDomain(entity));
		}
		return types;
	}

	public void addTypeRelationship(String fromType, String relationType,
			String toType) {
		
		MetaClassDO fromKlass = metaClassDAO.findByNameAndType(fromType, false);
		MetaClassDO relationshipKlass = metaClassDAO
			.findByNameAndType(relationType, true);
		MetaClassDO toKlass = metaClassDAO.findByNameAndType(toType, false);
		MetaClassRelationshipDO entity = new MetaClassRelationshipDO(fromKlass, 
				relationshipKlass, toKlass);
		metaClassRelationshipDAO.create(entity);
	}

	public void deleteTypeRelationship(String fromType,
			String relationType, String toType) {
		
		MetaClassRelationshipDO entity = metaClassRelationshipDAO.findByTypes(
				fromType, relationType, toType);
		metaClassRelationshipDAO.delete(entity);
	}

	public MetaClassRelationship getTypeRelationship(String fromType, 
			String relationType, String toType) {

		MetaClassRelationshipDO entity = metaClassRelationshipDAO.findByTypes(
				fromType, relationType, toType);
		return toDomain(entity);
	}

	public List<MetaClassRelationship> getTypeRelationshipsByFromType(
			String fromType) {
		
		List<MetaClassRelationshipDO> entities = metaClassRelationshipDAO
			.findByFromType(fromType);
		return toDomain(entities);
	}

	public List<MetaClassRelationship> getTypeRelationshipsByToType(
			String toType) {

		List<MetaClassRelationshipDO> entities = metaClassRelationshipDAO
			.findByToType(toType);
		return toDomain(entities);
	}

	public List<MetaClassRelationship> getTypeRelationshipsByRelationType(
			String relationType) {

		List<MetaClassRelationshipDO> entities = metaClassRelationshipDAO
			.findByRelationType(relationType);
		return toDomain(entities);
	}

	public List<MetaClassRelationship> getAllTypeRelationships() {
		List<MetaClassRelationshipDO> entities = metaClassRelationshipDAO
			.findAll();
		return toDomain(entities);
	}

	public void addTypeAttribute(String type, String attribute) {
		
		MetaClassDO typeDO = metaClassDAO.findByName(type);
		MetaAttributeDO attributeDO = null;
		try {
			attributeDO = metaAttributeDAO.findByName(attribute);
		} catch (EmptyResultDataAccessException e) {
			attributeDO = new MetaAttributeDO(attribute);
		}
		MetaClassAttributeDO entity = new MetaClassAttributeDO();
		entity.setType(typeDO);
		entity.setAttribute(attributeDO);
		metaClassAttributeDAO.create(entity);
	}

	public void deleteTypeAttribute(String type, String attribute) {

		MetaClassAttributeDO entity = metaClassAttributeDAO
				.findByTypeNameAndAttributeName(type, attribute);
		metaClassAttributeDAO.delete(entity);
	}

}
