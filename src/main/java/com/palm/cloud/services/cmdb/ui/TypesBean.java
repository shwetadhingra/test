package com.palm.cloud.services.cmdb.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.palm.cloud.services.cmdb.meta.MetaClass;

@ManagedBean
@RequestScoped
public class TypesBean {
	
	@ManagedProperty(value = "#{cmdbMetaService.allTypes}")
	private List<MetaClass> types;
	
	private List<SelectItem> typeOptions;
	
	private String value;

	@PostConstruct
	public void init() {
		typeOptions = new ArrayList<SelectItem>();
		for (MetaClass type : types) {
			if (!type.isRelationshipType())
				typeOptions.add(new SelectItem(type.getName(), type.getName()));
		}
	}
	
	public List<MetaClass> getTypes() {
		return types;
	}

	public void setTypes(List<MetaClass> types) {
		this.types = types;
	}

	public List<SelectItem> getTypeOptions() {
		return typeOptions;
	}

	public void setTypeOptions(List<SelectItem> typeOptions) {
		this.typeOptions = typeOptions;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
