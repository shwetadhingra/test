package com.palm.cloud.services.cmdb.collection;

import java.io.Serializable;

public class Link implements Serializable {

	private static final long serialVersionUID = 4113481661429712953L;

	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
	
}
