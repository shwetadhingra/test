package com.palm.cloud.services.cmdb.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.palm.cloud.services.cmdb.domain.CIObject;

public class Node implements Serializable {

	private static final long serialVersionUID = -7610047710495378699L;

	private CIObject object;
	
	private List<Link> links;

	public CIObject getObject() {
		return object;
	}

	public void setObject(CIObject object) {
		this.object = object;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		if (this.links == null) {
			this.links = new ArrayList<Link>();
		}
		this.links.add(link);
	}

}
