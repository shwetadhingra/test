package com.palm.cloud.services.cmdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.palm.cloud.services.cmdb.dao.IMetaAttributeDAO;
import com.palm.cloud.services.cmdb.dao.IMetaClassAttributeDAO;
import com.palm.cloud.services.cmdb.dao.IMetaClassDAO;
import com.palm.cloud.services.cmdb.dao.IMetaClassRelationshipDAO;
import com.palm.cloud.services.cmdb.dao.IMetaStatusDAO;
import com.palm.cloud.services.cmdb.dao.IObjectAttributeDAO;
import com.palm.cloud.services.cmdb.dao.IObjectDAO;
import com.palm.cloud.services.cmdb.dao.IRelationshipAttributeDAO;
import com.palm.cloud.services.cmdb.dao.IRelationshipDAO;
import com.palm.cloud.services.cmdb.dao.ObjectDAOImpl;
import com.palm.cloud.services.cmdb.dao.RelationshipDAOImpl;
import com.palm.cloud.services.cmdb.domain.CIAttribute;
import com.palm.cloud.services.cmdb.domain.CIObject;
import com.palm.cloud.services.cmdb.domain.CIRelationship;
import com.palm.cloud.services.cmdb.domain.CIRelationshipAttribute;
import com.palm.cloud.services.cmdb.entity.MetaAttributeDO;
import com.palm.cloud.services.cmdb.entity.MetaClassDO;
import com.palm.cloud.services.cmdb.entity.MetaClassRelationshipDO;
import com.palm.cloud.services.cmdb.entity.ObjectAttributeDO;
import com.palm.cloud.services.cmdb.entity.ObjectDO;
import com.palm.cloud.services.cmdb.entity.RelationshipAttributeDO;
import com.palm.cloud.services.cmdb.entity.RelationshipDO;
import com.palm.cloud.services.cmdb.meta.MetaAttribute;
import com.palm.cloud.services.cmdb.meta.MetaClass;
import com.palm.cloud.services.cmdb.meta.MetaClassRelationship;

public abstract class AbstractBaseServiceImpl {

	@Autowired
	protected IMetaStatusDAO metaStatusDAO;
	
	@Autowired
	protected IMetaAttributeDAO metaAttributeDAO;
	
	@Autowired
	protected IMetaClassDAO metaClassDAO;
	
	@Autowired
	protected IMetaClassRelationshipDAO metaClassRelationshipDAO;
	
	@Autowired
	protected IObjectDAO objectDAO;
	
	@Autowired
	protected IObjectAttributeDAO objectAttributeDAO;
	
	@Autowired
	protected IRelationshipDAO relationshipDAO;
	
	@Autowired
	protected IRelationshipAttributeDAO relationshipAttributeDAO;
	
	@Autowired
	protected IMetaClassAttributeDAO metaClassAttributeDAO;
	
	protected MetaClassDO toData(MetaClass domain) {
		MetaClassDO data = null;
		try {
			data = metaClassDAO.findByName(domain.getName());
		} catch (EmptyResultDataAccessException e) {
			data = new MetaClassDO();
		}
		data.setName(domain.getName());
		data.setRelationship(domain.isRelationshipType());
		if (domain.getAttributes() != null) {
			for (MetaAttribute attribute : domain.getAttributes()) {
				MetaAttributeDO ma = null;
				try {
					ma = metaAttributeDAO.findByName(
							attribute.getName());
				} catch (EmptyResultDataAccessException e) {
					ma = new MetaAttributeDO(attribute.getName());
				}
				data.addAttribute(ma);
			}
		} else {
			data.setAttributes(null);
		}
		if (domain.getParent() != null 
				&& domain.getParent().getName() != null) {
			
			data.setParent(metaClassDAO.findByName(domain.getParent()
					.getName()));
		} else {
			data.setParent(null);
		}
		return data;
	}
	
	protected RelationshipDO toData(CIRelationship domain, boolean isUpdate) {
		RelationshipDO data = null;
		if (domain.getNamespace() == null) {
			domain.setNamespace(RelationshipDAOImpl.DEFAULT_NAMESPACE);
		}
		if (isUpdate) {
			data = relationshipDAO.findByName(domain.getName());
		} else {
			data = new RelationshipDO();
		}
		data.setName(domain.getName());
		data.setNamespace(domain.getNamespace());
		MetaClassDO klass = metaClassDAO.findByName(domain.getType());
		data.setKlass(klass);
		data.setStatus(metaStatusDAO.findByName(domain.getStatus()));
		data.setFromObject(objectDAO.findByNameAndNamespace(
				domain.getFromObject(), domain.getNamespace()));
		data.setToObject(objectDAO.findByNameAndNamespace(domain.getToObject(), 
				domain.getNamespace()));
		if (domain.getAttributes() != null 
				&& domain.getAttributes().size() > 0) {
			
			for (CIRelationshipAttribute attribute : domain.getAttributes()) {
				if (klass.getAttribute(attribute.getName()) != null) {
					RelationshipAttributeDO ra = null;
					if (isUpdate) {
						ra = data.getAttribute(attribute.getName());
						if (ra != null) {
							data.getAttributes().remove(ra);
							ra.setValue(attribute.getValue());
						} else {
							ra = new RelationshipAttributeDO();
							ra.setAttribute(metaAttributeDAO.findByName(
									attribute.getName()));
							ra.setValue(attribute.getValue());
						}
					} else {
						ra = new RelationshipAttributeDO();
						ra.setAttribute(metaAttributeDAO.findByName(attribute
								.getName()));
						ra.setValue(attribute.getValue());
					}
					data.addAttribute(ra);
				}
			}
			if (isUpdate && data.getAttributes() != null) {
				List<RelationshipAttributeDO> dataAttributes 
					= new ArrayList<RelationshipAttributeDO>(data.getAttributes());
				for (RelationshipAttributeDO ra : dataAttributes) {
					if (domain.getAttribute(ra.getName()) == null) {
						data.getAttributes().remove(ra);
					}
				}
			}
		} else {
			if (data.getAttributes() != null) {
				data.getAttributes().clear();
			}
		}
		if (isValidRelationship(data.getFromType(), data.getType(), 
				data.getToType())) {
			return data;
		} else {
			return null;
		}
	}

	private boolean isValidRelationship(String fromType, String relationType, 
			String toType) {
		
		MetaClassRelationshipDO entity = metaClassRelationshipDAO.findByTypes(
				fromType, relationType, toType);
		return (entity != null);
	}
	
	protected ObjectDO toData(CIObject domain, boolean isUpdate) {
		ObjectDO data = null;
		if (domain.getNamespace() == null) {
			domain.setNamespace(ObjectDAOImpl.DEFAULT_NAMESPACE);
		}
		if (isUpdate) {
			data = objectDAO.findByNameAndNamespace(domain.getName(), 
					domain.getNamespace());
		} else {
			data = new ObjectDO();
		}
		data.setName(domain.getName());
		data.setNamespace(domain.getNamespace());
		MetaClassDO klass = metaClassDAO.findByName(domain.getType());
		data.setKlass(klass);
		data.setStatus(metaStatusDAO.findByName(domain.getStatus()));
		if (domain.getAttributes() != null 
				&& domain.getAttributes().size() > 0) {
			
			for (CIAttribute attribute : domain.getAttributes()) {
				if (klass.getAttribute(attribute.getName()) != null) {
					ObjectAttributeDO oa = null;
					if (isUpdate) {
						oa = data.getAttribute(attribute.getName());
						if (oa != null) {
							data.getAttributes().remove(oa);
							oa.setValue(attribute.getValue());
						} else {
							oa = new ObjectAttributeDO();
							oa.setAttribute(metaAttributeDAO.findByName(
									attribute.getName()));
							oa.setValue(attribute.getValue());
						}
					} else {
						oa = new ObjectAttributeDO();
						oa.setAttribute(metaAttributeDAO.findByName(
								attribute.getName()));
						oa.setValue(attribute.getValue());
					}
					data.addAttribute(oa);
				}
			}
			if (isUpdate && data.getAttributes() != null) {
				List<ObjectAttributeDO> dataAttributes 
					= new ArrayList<ObjectAttributeDO>(data.getAttributes());
				for (ObjectAttributeDO oa : dataAttributes) {
					if (domain.getAttribute(oa.getName()) == null) {
						data.getAttributes().remove(oa);
					}
				}
			}
		} else {
			if (data.getAttributes() != null) {
				data.getAttributes().clear();
			}
		}
		return data;
	}
	
	protected MetaClass toDomain(MetaClassDO data) {
		MetaClass domain = new MetaClass();
		domain.setName(data.getName());
		domain.setRelationshipType(data.isRelationship());
		if (data.getAttributes() != null) {
			for (MetaAttributeDO attribute : data.getAttributes()) {
				domain.addAttribute(new MetaAttribute(attribute.getName()));
			}
		}
		if (data.getParent() != null) {
			domain.setParent(toDomain(data.getParent()));
		}
		return domain;
	}

	protected List<MetaClassRelationship> toDomain(
			List<MetaClassRelationshipDO> entities) {
		
		List<MetaClassRelationship> relationships = null;
		if (entities != null) {
			relationships = new ArrayList<MetaClassRelationship>();
			for (MetaClassRelationshipDO entity : entities) {
				relationships.add(toDomain(entity));
			}
		}
		return relationships;
	}
	
	protected MetaClassRelationship toDomain(MetaClassRelationshipDO entity) {
		MetaClassRelationship relationship = new MetaClassRelationship();
		relationship.setFromType(toDomain(entity.getFromKlass()));
		relationship.setRelationshipType(
				toDomain(entity.getRelationshipKlass()));
		relationship.setToType(toDomain(entity.getToKlass()));
		return relationship;
	}

	protected CIObject toDomain(ObjectDO data) {
		CIObject domain = new CIObject(data.getName(), data.getNamespace(), 
				data.getKlass().getName(), data.getStatus().getName());
		if (data.getAttributes() != null) {
			for (ObjectAttributeDO attribute : data.getAttributes()) {
				if (data.getKlass().getAttribute(attribute.getName()) != null) {
					domain.addAttribute(new CIAttribute(
							attribute.getName(), attribute.getValue()));
				}
			}
		}
		return domain;
	}
	
	protected CIRelationship toDomain(RelationshipDO data) {
		CIRelationship domain = new CIRelationship(data.getNamespace(), 
				data.getFromObject().getName(), data.getToObject().getName(), 
				data.getKlass().getName(), data.getStatus().getName());
		domain.setName(data.getName());
		if (data.getAttributes() != null) {
			for (RelationshipAttributeDO attribute : data.getAttributes()) {
				domain.addAttribute(new CIRelationshipAttribute(
						attribute.getName(), attribute.getValue()));
			}
		}
		return domain;
	}

	protected List<CIAttribute> toAttributeDomains(
			List<ObjectAttributeDO> entities) {
		
		List<CIAttribute> domains = null;
		if (entities != null) {
			domains = new ArrayList<CIAttribute>();
			for (ObjectAttributeDO entity : entities) {
				domains.add(new CIAttribute(entity.getName(), 
						entity.getValue()));
			}
		}
		return domains;
	}

	protected List<CIObject> toObjectDomains(List<ObjectDO> entities) {
		List<CIObject> domains = null;
		if (entities != null) {
			domains = new ArrayList<CIObject>();
			for (ObjectDO entity : entities) {
				domains.add(toDomain(entity));
			}
		}
		return domains;
	}
	
	protected List<CIRelationship> toRelationDomains(
			List<RelationshipDO> entities) {
		
		List<CIRelationship> domains = null;
		if (entities != null) {
			domains = new ArrayList<CIRelationship>();
			for (RelationshipDO entity : entities) {
				domains.add(toDomain(entity));
			}
		}
		return domains;
	}
	
	protected List<CIRelationshipAttribute> toRelationAttributeDomains(
			List<RelationshipAttributeDO> entities) {
		
		List<CIRelationshipAttribute> domains = null;
		if (entities != null) {
			domains = new ArrayList<CIRelationshipAttribute>();
			for (RelationshipAttributeDO entity : entities) {
				domains.add(new CIRelationshipAttribute(entity.getName(), 
						entity.getValue()));
			}
		}
		return domains;
	}

}
