package com.dvt.standard.business.example.webservice;

import com.dvt.standard.commons.webservices.TypedResult;

public interface WorkFlowService {	
	TypedResult<String> startWorkflow(String taskSubject);
}
