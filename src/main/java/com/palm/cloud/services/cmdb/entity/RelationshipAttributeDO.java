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
@Table(name = "ci_relationship_attributes", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"relationship_id", "attribute_id"}))
@NamedQueries({
	@NamedQuery(name = "RelationshipAttributeDO.findByAttributeNameRelationNameAndNamespace", query = "SELECT ra from RelationshipAttributeDO ra where ra.relationship.name = :relationName and ra.relationship.namespace = :namespace and ra.attribute.name = :attributeName"),
	@NamedQuery(name = "RelationshipAttributeDO.findAllByRelationNameAndNamespace", query = "SELECT ra from RelationshipAttributeDO ra where ra.relationship.name = :relationName and ra.relationship.namespace = :namespace")
})			
public class RelationshipAttributeDO extends BaseDO 
		implements Serializable {

	private static final long serialVersionUID = -136819466186228956L;

	public RelationshipAttributeDO() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false,
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "relationship_id", 
			referencedColumnName = "relationship_id", nullable = false)
	private RelationshipDO relationship;

	@ManyToOne(optional = false, 
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", 
			nullable = false)
	private MetaAttributeDO attribute;
	
	@Column(name = "attribute_value", length = 4096)
	private String value;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
	
	public RelationshipDO getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationshipDO relationship) {
		this.relationship = relationship;
	}
	
}
