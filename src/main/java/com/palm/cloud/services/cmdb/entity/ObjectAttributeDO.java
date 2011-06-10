package com.palm.cloud.services.cmdb.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ci_object_attributes", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"object_id", "attribute_id"}))
@NamedQueries({
	@NamedQuery(name = "ObjectAttributeDO.findByAttributeNameObjectNameAndNamespace", query = "SELECT oa from ObjectAttributeDO oa where oa.object.name = :objectName and oa.object.namespace = :namespace and oa.attribute.name = :attributeName"),
	@NamedQuery(name = "ObjectAttributeDO.findAllByObjectNameAndNamespace", query = "SELECT oa from ObjectAttributeDO oa where oa.object.name = :objectName and oa.object.namespace = :namespace")
})			
public class ObjectAttributeDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -8032044878472060414L;

	public ObjectAttributeDO() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false,
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "object_id", referencedColumnName = "object_id", 
			nullable = false)
	private ObjectDO object;
	
	@ManyToOne(optional = false, 
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", 
			nullable = false, updatable = false)
	private MetaAttributeDO attribute;
	
	@Column(name = "attribute_value", length = 4096)
	private String value;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ObjectDO getObject() {
		return object;
	}

	public void setObject(ObjectDO object) {
		this.object = object;
	}

	public MetaAttributeDO getAttribute() {
		return attribute;
	}

	public void setAttribute(MetaAttributeDO attribute) {
		this.attribute = attribute;
	}

	public String getName() {
		return this.attribute.getName();
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
