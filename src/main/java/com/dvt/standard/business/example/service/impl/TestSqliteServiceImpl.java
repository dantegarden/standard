package com.dvt.standard.business.example.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvt.standard.business.example.service.TestSqliteService;
import com.dvt.standard.commons.query.SqliteQuery;
/**
 * springDataJpa不支持sqlite数据库
 * 只能手动控制事务
 * */
@Service
public class TestSqliteServiceImpl implements TestSqliteService{
	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	@Autowired
	private SqliteQuery sqliteQuery;

	@Override
	public void createExample() {
		sqliteQuery.executeUpdate("DROP TABLE IF EXISTS example0");
		String sql = "CREATE TABLE IF NOT EXISTS example0 (ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "NAME TEXT ,"
				+ "AGE INT )"; 
		sqliteQuery.executeUpdate(sql);
	}

	@Override
	public void insertRow() {
		String sql = "INSERT INTO example0 (ID,NAME,AGE) VALUES (null,'zhan',8)"; 
		sqliteQuery.executeUpdate(sql);
		sql = "INSERT INTO example0 (ID,NAME,AGE) VALUES (null,'wowo',12)"; 
		sqliteQuery.executeUpdate(sql);
		sql = "INSERT INTO example0 (ID,NAME,AGE) VALUES (null,null,14)"; 
		sqliteQuery.executeUpdate(sql);
	}
	
	@Override
	public void updateRow() {
		String sql = "UPDATE example0 set NAME = 'Micheal Zhan' where ID = 1 "; 
		sqliteQuery.executeUpdate(sql);
	}
	
	@Override
	public void deleteRow() {
		String sql = "DELETE FROM example0  where ID = 2 "; 
		sqliteQuery.executeUpdate(sql);
	}

	@Override
	public void search() {
		String sql = "SELECT name FROM example0";
		List<String> list = sqliteQuery.executeQuery(sql, null, String.class);
		System.out.println("query successfully");
	}
}
