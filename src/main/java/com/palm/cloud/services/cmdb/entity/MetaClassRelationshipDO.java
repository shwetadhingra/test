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
@Table(name = "ci_meta_class_relationships", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"from_class", "to_class", 
			"relationship_class"}))
@NamedQueries({
	@NamedQuery(name = "MetaClassRelationshipDO.findByFromType", query = "SELECT r FROM MetaClassRelationshipDO r WHERE r.fromKlass.name = :fromType"),
	@NamedQuery(name = "MetaClassRelationshipDO.findByToType", query = "SELECT r FROM MetaClassRelationshipDO r WHERE r.toKlass.name = :toType"),
	@NamedQuery(name = "MetaClassRelationshipDO.findByRelationType", query = "SELECT r FROM MetaClassRelationshipDO r WHERE r.relationshipKlass.name = :relationType"),
	@NamedQuery(name = "MetaClassRelationshipDO.findByTypes", query = "SELECT r FROM MetaClassRelationshipDO r WHERE r.fromKlass.name = :fromType and r.relationshipKlass.name = :relationType and r.toKlass.name = :toType")
})
public class MetaClassRelationshipDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 2454588007377382504L;

	public MetaClassRelationshipDO() {
		
	}
	
	public MetaClassRelationshipDO(MetaClassDO fromKlass, 
			MetaClassDO relationshipKlass, MetaClassDO toKlass) {
		this.fromKlass = fromKlass;
		this.relationshipKlass = relationshipKlass;
		this.toKlass = toKlass;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "from_class", referencedColumnName = "class_id", 
    		nullable = false)
	private MetaClassDO fromKlass;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "to_class", referencedColumnName = "class_id", 
    		nullable = false)
	private MetaClassDO toKlass;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "relationship_class", referencedColumnName = "class_id", 
    		nullable = false)
	private MetaClassDO relationshipKlass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MetaClassDO getFromKlass() {
		return fromKlass;
	}

	public void setFromKlass(MetaClassDO fromKlass) {
		this.fromKlass = fromKlass;
	}

	public MetaClassDO getToKlass() {
		return toKlass;
	}

	public void setToKlass(MetaClassDO toKlass) {
		this.toKlass = toKlass;
	}

	public MetaClassDO getRelationshipKlass() {
		return relationshipKlass;
	}

	public void setRelationshipKlass(MetaClassDO relationshipKlass) {
		this.relationshipKlass = relationshipKlass;
	}

}
