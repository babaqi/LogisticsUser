package com.logisticsUser.serviceBean;

import java.util.List;

import com.logisticsUser.model.ContextModel;

public class ContextBean extends BaseBean {

	private String count;

	public List<ContextModel> getContext() {
		return context;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public void setContext(List<ContextModel> context) {
		this.context = context;
	}

	private List<ContextModel> context;

}
