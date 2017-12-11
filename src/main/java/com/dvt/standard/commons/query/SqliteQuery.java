package com.dvt.standard.commons.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.alibaba.druid.pool.DruidDataSource;

public interface SqliteQuery {
	/**
	 * 做更新系操作
	 * */
	public int executeUpdate(String sql);
	/**
	 * 带参数列表的更新系操作
	 * */
	public int executeUpdate(String sql, List paraList);
	/**
	 * 查询并转化为对象列表
	 * @param sql
	 * @param paraList 参数列表
	 * @param className
	 * */
	public List executeQuery(String sql, List paraList, Class className);
}
