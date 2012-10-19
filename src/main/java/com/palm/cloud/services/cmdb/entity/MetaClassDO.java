package com.palm.cloud.services.cmdb.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ci_meta_classes")
@NamedQueries({
		@NamedQuery(name = "MetaClassDO.findByName", query = "SELECT c from MetaClassDO c WHERE c.name = :name"),
		@NamedQuery(name = "MetaClassDO.findByNameAndType", query = "SELECT c from MetaClassDO c WHERE c.name = :name and c.isRelationship = :isRelationshipType")
})
public class MetaClassDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 8664998156538173696L;

	public MetaClassDO() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "class_id")
	private Integer id;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY, 
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "parent_class_id", unique = true, nullable = true, 
			updatable = true)
	private MetaClassDO parent;
	
	@Column(name = "class_name", unique = true, nullable = false, length = 256)
	private String name;
	
	@Column(name = "is_relationship", nullable = false)
	private boolean isRelationship;
	
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, 
    		fetch = FetchType.EAGER)
	private List<MetaClassAttributeDO> classAttributes;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MetaClassDO getParent() {
		return parent;
	}

	public void setParent(MetaClassDO parent) {
		this.parent = parent;
	}

	public boolean isRelationship() {
		return isRelationship;
	}

	public void setRelationship(boolean isRelationship) {
		this.isRelationship = isRelationship;
	}

	public List<MetaAttributeDO> getAttributes() {
		List<MetaAttributeDO> attributes = null;
		if (classAttributes != null) {
			if (attributes == null) {
				attributes = new ArrayList<MetaAttributeDO>();
			}
			for (MetaClassAttributeDO classAttribute : classAttributes) {
				attributes.add(classAttribute.getAttribute());
			}
		}
		return attributes;
	}

	public void setAttributes(List<MetaAttributeDO> attributes) {
		if (attributes != null) {
			for (MetaAttributeDO attribute : attributes) {
				addAttribute(attribute);
			}
		}
	}

	public void addAttribute(MetaAttributeDO attribute) {
		addClassAttribute(new MetaClassAttributeDO(this, attribute));
	}

	public MetaAttributeDO getAttribute(String name) {
		MetaAttributeDO attribute = null;
		if (this.getAttributes() != null) {
			for (MetaAttributeDO ma : this.getAttributes()) {
				if (ma.getName().equals(name)) {
					attribute = ma;
					break;
				}
			}
		}
		return attribute;
	}
	
	public List<MetaClassAttributeDO> getClassAttributes() {
		return classAttributes;
	}

	public void setClassAttributes(List<MetaClassAttributeDO> classAttributes) {
		this.classAttributes = classAttributes;
	}

	public void addClassAttribute(MetaClassAttributeDO classAttribute) {
		if (classAttributes == null) {
			classAttributes = new ArrayList<MetaClassAttributeDO>();
		}
		classAttributes.add(classAttribute);
	}

}
