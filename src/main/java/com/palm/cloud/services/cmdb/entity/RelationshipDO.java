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
@Table(name = "ci_relationships", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"relationship_name", "namespace"}))
@NamedQueries({
	@NamedQuery(name = "RelationshipDO.findByNameAndNamespace", query = "SELECT r from RelationshipDO r where r.name = :name and r.namespace = :namespace"),
	@NamedQuery(name = "RelationshipDO.findByFromObjectNamespaceAndClass", query = "SELECT r from RelationshipDO r where r.fromObject.name = :objectName and r.fromObject.namespace = :namespace and r.namespace = :namespace and r.klass.name = :className"),
	@NamedQuery(name = "RelationshipDO.findByToObjectNamespaceAndClass", query = "SELECT r from RelationshipDO r where r.toObject.name = :objectName and r.toObject.namespace = :namespace and r.namespace = :namespace and r.klass.name = :className"),
	@NamedQuery(name = "RelationshipDO.findByFromObjectAndNamespace", query = "SELECT r from RelationshipDO r where r.fromObject.name = :objectName and r.fromObject.namespace = :namespace and r.namespace = :namespace"),
	@NamedQuery(name = "RelationshipDO.findByToObjectAndNamespace", query = "SELECT r from RelationshipDO r where r.toObject.name = :objectName and r.toObject.namespace = :namespace and r.namespace = :namespace"),
	@NamedQuery(name = "RelationshipDO.findAllByClassAndNamespace", query = "SELECT r from RelationshipDO r where r.klass.name = :className and r.namespace = :namespace"),
	@NamedQuery(name = "RelationshipDO.findAllByNamespace", query = "SELECT r from RelationshipDO r where r.namespace = :namespace")
})			
public class RelationshipDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -4466901083913739474L;

	public RelationshipDO() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "relationship_id")
	private Integer id;
	
	@Column(name = "relationship_name", nullable = false, length = 512)
	private String name;
	
	@Column(name = "namespace", nullable = false, length = 256)
	private String namespace;
	
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", 
    		nullable = false)
	private MetaClassDO klass;
	
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", 
    		nullable = false)
	private MetaStatusDO status;
	
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "from_object", referencedColumnName = "object_id", 
    		nullable = false)
	private ObjectDO fromObject;
	
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "to_object", referencedColumnName = "object_id", 
    		nullable = false)
	private ObjectDO toObject;
	
	@OneToMany(mappedBy = "relationship", cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, orphanRemoval = true)
	private List<RelationshipAttributeDO> attributes;
	
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

	public String getType() {
		return this.klass.getName();
	}
	
	public MetaStatusDO getStatus() {
		return status;
	}

	public void setStatus(MetaStatusDO status) {
		this.status = status;
	}

	public ObjectDO getFromObject() {
		return fromObject;
	}

	public void setFromObject(ObjectDO fromObject) {
		this.fromObject = fromObject;
	}

	public String getFromType() {
		return this.fromObject.getType();
	}
	
	public ObjectDO getToObject() {
		return toObject;
	}

	public void setToObject(ObjectDO toObject) {
		this.toObject = toObject;
	}
	
	public String getToType() {
		return this.toObject.getType();
	}
	
	public List<RelationshipAttributeDO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<RelationshipAttributeDO> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(RelationshipAttributeDO attribute) {
		attribute.setRelationship(this);
		if (attributes == null) {
			attributes = new ArrayList<RelationshipAttributeDO>();
		}
		attributes.add(attribute);
	}

	public RelationshipAttributeDO getAttribute(String attributeName) {
		RelationshipAttributeDO attribute = null;
		if (this.getAttributes() != null) {
			for (RelationshipAttributeDO rao : this.getAttributes()) {
				if (rao.getName().equals(attributeName)) {
					attribute = rao;
					break;
				}
			}
		}
		return attribute;
	}
	
}
