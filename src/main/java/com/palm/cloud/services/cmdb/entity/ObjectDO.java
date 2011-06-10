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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ci_objects", 
	uniqueConstraints = 
		@UniqueConstraint(columnNames = {"object_name", "namespace"}
))
@NamedQueries({
	@NamedQuery(name = "ObjectDO.findByNameAndNamespace", query = "SELECT o from ObjectDO o where o.name = :name and o.namespace = :namespace"),
	@NamedQuery(name = "ObjectDO.findAllByClassAndNamespace", query = "SELECT o from ObjectDO o where o.klass.name = :className and o.namespace = :namespace"),
	@NamedQuery(name = "ObjectDO.findFromObjectsByClassAndNamespace", query = "SELECT r.fromObject from RelationshipDO r where r.toObject.name = :name and r.toObject.namespace = :namespace and r.klass.name = :relationClass and r.fromObject.klass.name = :className"),
	@NamedQuery(name = "ObjectDO.findToObjectsByClassAndNamespace", query = "SELECT r.toObject from RelationshipDO r where r.fromObject.name = :name and r.fromObject.namespace = :namespace and r.klass.name = :relationClass and r.toObject.klass.name = :className"),
	@NamedQuery(name = "ObjectDO.findFromObjectsByRelationshipClassAndNamespace", query = "SELECT r.fromObject from RelationshipDO r where r.toObject.name = :name and r.toObject.namespace = :namespace and r.klass.name = :relationClass"),
	@NamedQuery(name = "ObjectDO.findToObjectsByRelationshipClassAndNamespace", query = "SELECT r.toObject from RelationshipDO r where r.fromObject.name = :name and r.fromObject.namespace = :namespace and r.klass.name = :relationClass"),
	@NamedQuery(name = "ObjectDO.findFromObjectsByNamespace", query = "SELECT r.fromObject from RelationshipDO r where r.toObject.name = :name and r.toObject.namespace = :namespace"),
	@NamedQuery(name = "ObjectDO.findToObjectsByNamespace", query = "SELECT r.toObject from RelationshipDO r where r.fromObject.name = :name and r.fromObject.namespace = :namespace")
})			
public class ObjectDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 291295294252533986L;

	public ObjectDO() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "object_id")
	private Integer id;
	
	@Column(name = "object_name", nullable = false, length = 200)
	private String name;
	
	@Column(name = "namespace", nullable = false, length = 256)
	private String namespace;
	
    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", 
    		nullable = false)
	private MetaClassDO klass;
	
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", 
    		nullable = false)
	private MetaStatusDO status;
	
    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, 
    		fetch = FetchType.LAZY)
    private List<ObjectAttributeDO> attributes;
	
	@OneToMany(mappedBy = "fromObject", 
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, 
			fetch = FetchType.LAZY)
	private List<RelationshipDO> fromRelationships;
	
	@OneToMany(mappedBy = "toObject", 
			cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, 
			fetch = FetchType.LAZY)
	private List<RelationshipDO> toRelationships;
	
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

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public MetaClassDO getKlass() {
		return klass;
	}

	public void setKlass(MetaClassDO klass) {
		this.klass = klass;
	}

	public MetaStatusDO getStatus() {
		return status;
	}

	public void setStatus(MetaStatusDO status) {
		this.status = status;
	}

	public List<ObjectAttributeDO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ObjectAttributeDO> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(ObjectAttributeDO attribute) {
		attribute.setObject(this);
		if (attributes == null) {
			attributes = new ArrayList<ObjectAttributeDO>();
		}
		attributes.add(attribute);
	}
	
	public List<RelationshipDO> getFromRelationships() {
		return fromRelationships;
	}

	public void setFromRelationships(List<RelationshipDO> fromRelationships) {
		this.fromRelationships = fromRelationships;
	}

	public void addFromRelationship(RelationshipDO relationship) {
		relationship.setFromObject(this);
		if (fromRelationships == null) {
			fromRelationships = new ArrayList<RelationshipDO>();
		}
		fromRelationships.add(relationship);
	}
	
	public List<RelationshipDO> getToRelationships() {
		return toRelationships;
	}

	public void setToRelationships(List<RelationshipDO> toRelationships) {
		this.toRelationships = toRelationships;
	}

	public void addToRelationship(RelationshipDO relationship) {
		relationship.setToObject(this);
		if (toRelationships == null) {
			toRelationships = new ArrayList<RelationshipDO>();
		}
		toRelationships.add(relationship);
	}
	
}
