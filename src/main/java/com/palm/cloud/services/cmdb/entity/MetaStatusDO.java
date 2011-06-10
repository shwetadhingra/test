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
@Table(name = "ci_meta_statuses")
@NamedQueries({
	@NamedQuery(name = "MetaStatusDO.findByName", query = "SELECT s from MetaStatusDO s where s.name = :status")
})
public class MetaStatusDO extends BaseDO implements Serializable {

	private static final long serialVersionUID = -8735507268360307597L;

	public MetaStatusDO() {
		
	}
	
	public MetaStatusDO(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "status_id")
	private Integer id;
	
	@Column(name = "status_name", unique = true, nullable = false, length = 256)
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
