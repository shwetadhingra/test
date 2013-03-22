package com.palm.cloud.services.cmdb.collection;

import java.io.Serializable;

public class Link implements Serializable {

	private static final long serialVersionUID = 4113481661429712953L;

	private Node node;
	
	private String type;
	
	private boolean isForward;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isForward() {
		return isForward;
	}

	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}

}
