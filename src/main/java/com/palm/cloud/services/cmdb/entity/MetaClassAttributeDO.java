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
@Table(name = "ci_meta_class_attributes", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"class_id", "attribute_id"}))
@NamedQueries({
	@NamedQuery(name = "MetaClassAttributeDO.findByClassNameAndAttributeName", query = "SELECT mca from MetaClassAttributeDO mca where mca.type.name = :typeName and mca.attribute.name = :attributeName")
})			
public class MetaClassAttributeDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 2601883591558614209L;

	public MetaClassAttributeDO() {
		
	}
	
	public MetaClassAttributeDO(MetaClassDO type, MetaAttributeDO attribute) {
		this.type = type;
		this.attribute = attribute;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false,
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "class_id", referencedColumnName = "class_id", 
			nullable = false)
	private MetaClassDO type;
	
	@ManyToOne(optional = false, 
			cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", 
			nullable = false, updatable = false)
	private MetaAttributeDO attribute;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MetaClassDO getType() {
		return type;
	}

	public void setType(MetaClassDO type) {
		this.type = type;
	}

	public MetaAttributeDO getAttribute() {
		return attribute;
	}

	public void setAttribute(MetaAttributeDO attribute) {
		this.attribute = attribute;
	}
	
}
