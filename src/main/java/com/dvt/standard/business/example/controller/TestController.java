package com.dvt.standard.business.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dvt.standard.business.example.service.TestService;
import com.dvt.standard.business.example.service.TestSqliteService;
import com.dvt.standard.business.example.webservice.WorkFlowService;
import com.dvt.standard.commons.GlobalConstants;
import com.dvt.standard.commons.entity.Result;



@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;
	@Autowired
	private TestSqliteService testSqliteService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@RequestMapping
	public String init() {
		System.out.println("test initting");
		
		Result r = workFlowService.startWorkflow("{\"processDefinitionKey\": \"jgdqcgcjsp\", \"userId\": 6}");
		
//		//建表
//		testSqliteService.createExample();
//		//插入
//		testSqliteService.insertRow();
//		//更新
//		testSqliteService.updateRow();
//		//查询
//		testSqliteService.search();
//		//删除
//		testSqliteService.deleteRow();
		return GlobalConstants.PAGE_TEST;
	}
}
