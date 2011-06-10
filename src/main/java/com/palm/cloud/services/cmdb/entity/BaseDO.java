package com.palm.cloud.services.cmdb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseDO implements Serializable {

	private static final long serialVersionUID = -2886021282889972142L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, insertable = true, 
			updatable = false)
	protected Date created;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", insertable = false, updatable = true)
	protected Date updated;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@PrePersist
	protected void onCreate() {
		this.setCreated(new Date(System.currentTimeMillis()));
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.setUpdated(new Date(System.currentTimeMillis()));
	}
	
}
