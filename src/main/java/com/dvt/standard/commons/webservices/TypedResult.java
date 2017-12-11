package com.dvt.standard.commons.webservices;

import com.dvt.standard.commons.entity.Result;

public class TypedResult<T> extends Result {
	
	private T typedDatas;
	
	public TypedResult(){
		super();
	}
	
	public TypedResult(Boolean success, String msg, T typedDatas) {
		super(success, msg, null);
		this.typedDatas = typedDatas;
	}
	
	@Override
	public T getData() {
		return typedDatas;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setData(Object data) {
		this.typedDatas = (T) data;
	}
}
