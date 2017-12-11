package com.dvt.standard.business.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dvt.standard.business.example.service.TestService;
import com.dvt.standard.commons.query.DynamicQuery;
@Transactional
@Service
public class TestServiceImpl implements TestService{
	
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private DynamicQuery dynamicQuery;
	
}
