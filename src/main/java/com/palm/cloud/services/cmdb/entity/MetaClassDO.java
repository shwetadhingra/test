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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, 
			CascadeType.MERGE})
	@JoinTable(name = "ci_meta_class_attributes", 
			joinColumns = {@JoinColumn(name = "class_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "attribute_id", 
					unique = false)},
			uniqueConstraints = @UniqueConstraint(columnNames = {"class_id", 
					"attribute_id"}))
	@OrderBy(value = "name")
	private List<MetaAttributeDO> attributes;
	
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
		return attributes;
	}

	public void setAttributes(List<MetaAttributeDO> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(MetaAttributeDO attribute) {
		if (attributes == null) {
			attributes = new ArrayList<MetaAttributeDO>();
		}
		attributes.add(attribute);
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
	
}
