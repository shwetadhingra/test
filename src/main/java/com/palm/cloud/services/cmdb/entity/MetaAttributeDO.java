package com.palm.cloud.services.cmdb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ci_meta_attributes")
@NamedQueries({
	@NamedQuery(name = "MetaAttributeDO.findByName", query = "SELECT a from MetaAttributeDO a where a.name = :attributeName")
})
public class MetaAttributeDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = 6576165239637246767L;

	public MetaAttributeDO() {
		
	}
	
	public MetaAttributeDO(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "attribute_id")
	private Integer id;
	
	@Column(name = "attribute_name", unique = true, nullable = false, 
			length = 256)
	private String name;
	
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

}
