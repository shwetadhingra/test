package com.palm.cloud.services.cmdb.service;

import java.util.List;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;

import com.palm.cloud.services.cmdb.condition.Condition;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectDO;
import com.palm.cloud.services.cmdb.entity.RelationshipAttributeDO;
import com.palm.cloud.services.cmdb.entity.RelationshipDO;

@Transactional
@WebService
public class CMDBDataServiceImpl extends AbstractBaseServiceImpl 
		implements ICMDBDataService {

	public void addObject(CIObject object) {
		ObjectDO entity = toData(object, false);
		objectDAO.create(entity);
	}

	public void updateObject(CIObject object) {
		ObjectDO entity = objectDAO.findByName(object.getName());
		if (entity != null) {
			entity = toData(object, true);
			entity = objectDAO.update(entity);
		}
	}

	public void deleteObject(String name) {
		ObjectDO entity = objectDAO.findByName(name);
		objectDAO.delete(entity);
	}

	public void deleteObjectNS(String name, String namespace) {
		ObjectDO entity = objectDAO.findByNameAndNamespace(name, namespace);
		objectDAO.delete(entity);
	}

	public CIObject getObject(String name) {
		ObjectDO entity = objectDAO.findByName(name);
		return toDomain(entity);
	}

	public CIObject getObjectNS(String name, String namespace) {
		ObjectDO entity = objectDAO.findByNameAndNamespace(name, namespace);
		return toDomain(entity);
	}

	public List<CIObject> getObjects(String type, int offset, int maxResults) {
		List<ObjectDO> entities = objectDAO.findAllByClass(type, offset, 
				maxResults);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsNS(String type, String namespace, 
			int offset, int maxResults) {

		List<ObjectDO> entities = objectDAO.findAllByClassAndNamespace(type, 
				namespace, offset, maxResults);
		return toObjectDomains(entities);
	}

	public void addAttribute(String objectName, String attributeName,
			String attributeValue) {
		
		ObjectDO object = objectDAO.findByName(objectName);
		ObjectAttributeDO entity = new ObjectAttributeDO();
		MetaAttributeDO attribute = object.getKlass()
			.getAttribute(attributeName);
		if (attribute != null) {
			entity.setAttribute(attribute);
			entity.setValue(attributeValue);
			entity.setObject(object);
			objectAttributeDAO.create(entity);
		}
	}

	public void addAttributeNS(String objectName, String namespace,
			String attributeName, String attributeValue) {

		ObjectDO object = objectDAO.findByNameAndNamespace(
				objectName, namespace);
		ObjectAttributeDO entity = new ObjectAttributeDO();
		MetaAttributeDO attribute = object.getKlass()
			.getAttribute(attributeName);
		if (attribute != null) {
			entity.setAttribute(attribute);
			entity.setValue(attributeValue);
			entity.setObject(object);
			objectAttributeDAO.create(entity);
		}
	}

	public void updateAttribute(String objectName, String attributeName,
			String attributeValue) {

		ObjectAttributeDO entity = objectAttributeDAO.findByName(objectName, 
				attributeName);
		entity.setValue(attributeValue);
		objectAttributeDAO.update(entity);
	}

	public void updateAttributeNS(String objectName, String namespace,
			String attributeName, String attributeValue) {
		
		ObjectAttributeDO entity = objectAttributeDAO.findByNameAndNamespace(
				objectName, namespace, attributeName);
		entity.setValue(attributeValue);
		objectAttributeDAO.update(entity);
	}

	public void deleteAttribute(String objectName, String attributeName) {
		ObjectAttributeDO entity = objectAttributeDAO.findByName(objectName, 
				attributeName);
		objectAttributeDAO.delete(entity);
	}

	public void deleteAttributeNS(String objectName, String namespace,
			String attributeName) {

		ObjectAttributeDO entity = objectAttributeDAO.findByNameAndNamespace(
				objectName, namespace, attributeName);
		objectAttributeDAO.delete(entity);
	}

	public CIAttribute getAttribute(String objectName, String attributeName) {
		ObjectAttributeDO entity = objectAttributeDAO.findByName(objectName, 
				attributeName);
		return new CIAttribute(entity.getName(), entity.getValue());
	}

	public CIAttribute getAttributeNS(String objectName, String namespace,
			String attributeName) {

		ObjectAttributeDO entity = objectAttributeDAO.findByNameAndNamespace(
				objectName, namespace, attributeName);
		return new CIAttribute(entity.getName(), entity.getValue());
	}

	public List<CIAttribute> getAttributes(String objectName) {
		List<ObjectAttributeDO> entities = objectAttributeDAO
			.findAllByObjectName(objectName);
		return toAttributeDomains(entities);
	}

	public List<CIAttribute> getAttributesNS(String objectName, 
			String namespace) {

		List<ObjectAttributeDO> entities = objectAttributeDAO
			.findAllByObjectNameAndNamespace(objectName, namespace);
		return toAttributeDomains(entities);
	}

	public void addRelation(CIRelationship relation) {
		StringBuilder derivedName = new StringBuilder();
		derivedName.append(relation.getFromObject())
			.append("~")
			.append(relation.getType())
			.append("~")
			.append(relation.getToObject());
		relation.setName(derivedName.toString());
		RelationshipDO entity = toData(relation, false);
		relationshipDAO.create(entity);
	}

	public void updateRelation(CIRelationship relation) {
		StringBuilder derivedName = new StringBuilder();
		derivedName.append(relation.getFromObject())
			.append("~")
			.append(relation.getType())
			.append("~")
			.append(relation.getToObject());
		relation.setName(derivedName.toString());
		RelationshipDO entity = relationshipDAO.findByName(relation.getName());
		if (entity != null) {
			entity = toData(relation, true);
			entity = relationshipDAO.update(entity);
		}
	}

	public void deleteRelation(String name) {
		RelationshipDO entity = relationshipDAO.findByName(name);
		relationshipDAO.delete(entity);
	}

	public void deleteRelationNS(String name, String namespace) {
		RelationshipDO entity = relationshipDAO.findByNameAndNamespace(
				name, namespace);
		relationshipDAO.delete(entity);
	}

	public CIRelationship getRelation(String name) {
		RelationshipDO entity = relationshipDAO.findByName(name);
		return toDomain(entity);
	}

	public CIRelationship getRelationNS(String name, String namespace) {
		RelationshipDO entity = relationshipDAO.findByNameAndNamespace(name, 
				namespace);
		return toDomain(entity);
	}

	public List<CIRelationship> getRelations(String relationType, int offset,
			int maxResults) {
		
		List<RelationshipDO> entities = relationshipDAO.findAllByClass(
				relationType, offset, maxResults);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getRelationsNS(String relationType,
			String namespace, int offset, int maxResults) {

		List<RelationshipDO> entities = relationshipDAO
			.findAllByClassAndNamespace(relationType, namespace, offset, 
					maxResults);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getFromRelations(String objectName,
			String relationType) {
		
		List<RelationshipDO> entities = relationshipDAO
			.findByFromObjectNameAndClass(objectName, relationType);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getFromRelationsNS(String objectName,
			String namespace, String relationType) {

		List<RelationshipDO> entities = relationshipDAO
			.findByFromObjectNamespaceAndClass(objectName, namespace, 
					relationType);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getToRelations(String objectName,
			String relationType) {

		List<RelationshipDO> entities = relationshipDAO
			.findByToObjectNameAndClass(objectName, relationType);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getToRelationsNS(String objectName,
			String namespace, String relationType) {

		List<RelationshipDO> entities = relationshipDAO
			.findByToObjectNamespaceAndClass(objectName, namespace, 
					relationType);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getAllFromRelations(String objectName) {
		List<RelationshipDO> entities = relationshipDAO.findByFromObjectName(
				objectName);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getAllFromRelationsNS(String objectName,
			String namespace) {

		List<RelationshipDO> entities = relationshipDAO
			.findByFromObjectNameAndNamespace(objectName, namespace);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getAllToRelations(String objectName) {
		List<RelationshipDO> entities = relationshipDAO.findByToObjectName(
				objectName);
		return toRelationDomains(entities);
	}

	public List<CIRelationship> getAllToRelationsNS(String objectName,
			String namespace) {

		List<RelationshipDO> entities = relationshipDAO
			.findByToObjectNameAndNamespace(objectName, namespace);
		return toRelationDomains(entities);
	}

	public void addRelationAttribute(String relationName, String attributeName,
			String attributeValue) {

		RelationshipDO relation = relationshipDAO.findByName(relationName);
		RelationshipAttributeDO entity = new RelationshipAttributeDO();
		MetaAttributeDO attribute = relation.getKlass()
			.getAttribute(attributeName);
		if (attribute != null) {
			entity.setAttribute(attribute);
			entity.setValue(attributeValue);
			entity.setRelationship(relation);
			relationshipAttributeDAO.create(entity);
		}
	}

	public void addRelationAttributeNS(String relationName, String namespace,
			String attributeName, String attributeValue) {

		RelationshipDO relation = relationshipDAO.findByNameAndNamespace(
				relationName, namespace);
		RelationshipAttributeDO entity = new RelationshipAttributeDO();
		MetaAttributeDO attribute = relation.getKlass()
			.getAttribute(attributeName);
		if (attribute != null) {
			entity.setAttribute(attribute);
			entity.setValue(attributeValue);
			entity.setRelationship(relation);
			relationshipAttributeDAO.create(entity);
		}
	}

	public void updateRelationAttribute(String relationName,
			String attributeName, String attributeValue) {
		
		RelationshipAttributeDO entity = relationshipAttributeDAO.findByName(
				relationName, attributeName);
		entity.setValue(attributeValue);
		relationshipAttributeDAO.update(entity);
	}

	public void updateRelationAttributeNS(String relationName, String namespace,
			String attributeName, String attributeValue) {
		
		RelationshipAttributeDO entity = relationshipAttributeDAO
			.findByNameAndNamespace(relationName, namespace, attributeName);
		entity.setValue(attributeValue);
		relationshipAttributeDAO.update(entity);
	}

	public void deleteRelationAttribute(String relationName,
			String attributeName) {
		
		RelationshipAttributeDO entity = relationshipAttributeDAO.findByName(
				relationName, attributeName);
		relationshipAttributeDAO.delete(entity);
	}

	public void deleteRelationAttributeNS(String relationName, String namespace,
			String attributeName) {
		
		RelationshipAttributeDO entity = relationshipAttributeDAO
			.findByNameAndNamespace(relationName, namespace, attributeName);
		relationshipAttributeDAO.delete(entity);
	}

	public CIRelationshipAttribute getRelationAttribute(String relationName,
			String attributeName) {

		RelationshipAttributeDO entity = relationshipAttributeDAO.findByName(
				relationName, attributeName);
		return new CIRelationshipAttribute(entity.getName(), entity.getValue());
	}

	public CIRelationshipAttribute getRelationAttributeNS(String relationName,
			String namespace, String attributeName) {

		RelationshipAttributeDO entity = relationshipAttributeDAO
			.findByNameAndNamespace(relationName, namespace, attributeName);
		return new CIRelationshipAttribute(entity.getName(), entity.getValue());
	}

	public List<CIRelationshipAttribute> getRelationAttributes(
			String relationName) {
		
		List<RelationshipAttributeDO> entities = relationshipAttributeDAO
			.findAllByRelationName(relationName);
		return toRelationAttributeDomains(entities);
	}

	public List<CIRelationshipAttribute> getRelationAttributesNS(
			String relationName, String namespace) {

		List<RelationshipAttributeDO> entities = relationshipAttributeDAO
			.findAllByRelationNameAndNamespace(relationName, namespace);
		return toRelationAttributeDomains(entities);
	}

	public List<CIObject> getFromObjectsByType(String objectName,
			String relationType, String type) {
		
		List<ObjectDO> entities = objectDAO.findFromObjectsByClass(
				objectName, relationType, type);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsByTypeNS(String objectName, 
			String namespace, String relationType, String type) {

		List<ObjectDO> entities = objectDAO.findFromObjectsByClassAndNamespace(
				objectName, namespace, relationType, type);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByType(String objectName,
			String relationType, String type) {

		List<ObjectDO> entities = objectDAO.findToObjectsByClass(objectName, 
				relationType, type);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByTypeNS(String objectName, 
			String namespace, String relationType, String type) {

		List<ObjectDO> entities = objectDAO.findToObjectsByClassAndNamespace(
				objectName, namespace, relationType, type);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsByRelationType(String objectName,
			String relationType) {
		
		List<ObjectDO> entities = objectDAO.findFromObjectsByRelationClass(
				objectName, relationType);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsByRelationTypeNS(String objectName,
			String namespace, String relationType) {
		
		List<ObjectDO> entities = objectDAO
			.findFromObjectsByRelationClassAndNamespace(
				objectName, namespace, relationType);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByRelationType(String objectName,
			String relationType) {

		List<ObjectDO> entities = objectDAO.findToObjectsByRelationClass(
				objectName, relationType);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByRelationTypeNS(String objectName,
			String namespace, String relationType) {
		
		List<ObjectDO> entities = objectDAO
			.findToObjectsByRelationClassAndNamespace(
				objectName, namespace, relationType);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjects(String objectName) {
		List<ObjectDO> entities = objectDAO.findFromObjects(objectName);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsNS(String objectName, 
			String namespace) {
		
		List<ObjectDO> entities = objectDAO.findFromObjectsByNamespace(
				objectName, namespace);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjects(String objectName) {
		List<ObjectDO> entities = objectDAO.findToObjects(objectName);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsNS(String objectName, String namespace) {
		List<ObjectDO> entities = objectDAO.findToObjectsByNamespace(
				objectName, namespace);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByConditionsNS(String type,
			String namespace, int offset, int maxResults,
			Condition... conditions) {
		
		List<ObjectDO> entities = objectDAO.findAllByConditionsAndNamespace(
				type, namespace, offset, maxResults, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByConditions(String type, int offset,
			int maxResults, Condition... conditions) {
		
		List<ObjectDO> entities = objectDAO.findAllByConditions(type, 
				offset, maxResults, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsByConditions(String objectName,
			String relationType, String type, Condition... conditions) {

		List<ObjectDO> entities = objectDAO.findFromObjectsByConditions(
				objectName, relationType, type, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getFromObjectsByConditionsNS(String objectName,
			String namespace, String relationType, String type,
			Condition... conditions) {

		List<ObjectDO> entities = objectDAO
			.findFromObjectsByConditionsAndNamespace(objectName, namespace, 
					relationType, type, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByConditions(String objectName,
			String relationType, String type, Condition... conditions) {
		
		List<ObjectDO> entities = objectDAO.findToObjectsByConditions(
				objectName, relationType, type, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getToObjectsByConditionsNS(String objectName,
			String namespace, String relationType, String type,
			Condition... conditions) {
		
		List<ObjectDO> entities = objectDAO
			.findToObjectsByConditionsAndNamespace(objectName, namespace, 
					relationType, type, conditions);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByNameAndType(String objectNameLike, 
			String type, int offset, int maxResults) {
		
		List<ObjectDO> entities = objectDAO.findAllByNameAndClass(
				objectNameLike, type, offset, maxResults);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByNameAndTypeNS(String objectNameLike,
			String namespace, String type, int offset, int maxResults) {

		List<ObjectDO> entities = objectDAO.findAllByNameClassAndNamespace(
				objectNameLike, type, namespace, offset, maxResults);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByName(String objectNameLike,
			int offset, int maxResults) {
		
		List<ObjectDO> entities = objectDAO.findAllByName(
				objectNameLike, offset, maxResults);
		return toObjectDomains(entities);
	}

	public List<CIObject> getObjectsByNameNS(String objectNameLike,
			String namespace, int offset, int maxResults) {

		List<ObjectDO> entities = objectDAO.findAllByNameAndNamespace(
				objectNameLike, namespace, offset, maxResults);
		return toObjectDomains(entities);
	}

}
