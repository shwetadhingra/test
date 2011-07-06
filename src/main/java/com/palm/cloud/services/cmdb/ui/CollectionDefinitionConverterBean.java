package com.palm.cloud.services.cmdb.ui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.palm.cloud.services.cmdb.collection.xml.Vertex;

@ManagedBean
@RequestScoped
public class CollectionDefinitionConverterBean extends BaseConverterBean {

	protected Class<?> getType() {
		return Vertex.class;
	}

}
