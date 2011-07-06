package com.palm.cloud.services.cmdb.ui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.palm.cloud.services.cmdb.condition.Condition;

@ManagedBean
@RequestScoped
public class ConditionConverterBean extends BaseConverterBean {

	protected Class<?> getType() {
		return Condition.class;
	}

}
