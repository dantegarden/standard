package com.dvt.standard.business.example.webservice.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dvt.standard.business.example.webservice.WorkFlowService;
import com.dvt.standard.commons.webservices.TypedResult;
import com.dvt.standard.commons.webservices.WebServices;
@Service
public class WorkFlowServiceImpl implements WorkFlowService{
	
	private static final Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);
	private static final String ERR_MSG = "调用webservice时发生异常";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final TypedResult<?> nullResp = new TypedResult(Boolean.FALSE,"调用webservice返回结果为空", null);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final TypedResult<?> errResp = new TypedResult(Boolean.FALSE,ERR_MSG, null);
	
	@Autowired
	private WebServices webServices;
	
	@Override
	public TypedResult<String> startWorkflow(String jsonStr) {
		return this.doInvokeWebService("exampleService",
									   "startWorkflow", 
									   new TypeReference<String>(){},
									   jsonStr);
	}
	
	@SuppressWarnings({ "unchecked"})
	private <T> TypedResult<T> parseResult(String invokeResult,TypeReference<T> dataTypeToken){
		if(invokeResult==null){
			return (TypedResult<T>) nullResp;
		}
		TypedResult<T> result = JSON.parseObject(invokeResult,
				new TypeReference<TypedResult<T>>(){});
		
		if(result.getData() instanceof JSON){
			JSON data = (JSON)result.getData();
			result.setData(JSON.parseObject(data.toJSONString(),
					dataTypeToken));
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked" })
	private <T> TypedResult<T> doInvokeWebService(String serviceName,String methodName ,TypeReference<T> dataType,Object... params){
		String invokeResult = null;
		try {
			invokeResult = this.invokeService(serviceName,methodName,params);
		} catch (Exception e) {
			logger.error(ERR_MSG,e);
			return (TypedResult<T>) errResp;
		}
		return parseResult(invokeResult,dataType) ;
	}
	
	private String invokeService(String serviceName,String methodName,Object...params) throws Exception{
//		webServices.initClients();//FIXME
		return this.webServices.invoke(serviceName, methodName, params);
	}
}
