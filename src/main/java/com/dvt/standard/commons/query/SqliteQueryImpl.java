package com.dvt.standard.commons.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.BindException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.pool.DruidDataSource;
import com.dvt.standard.commons.exception.BiException;
import com.dvt.standard.commons.utils.CommonHelper;

@Repository
public class SqliteQueryImpl implements SqliteQuery{
	
	private static final Logger logger = LoggerFactory.getLogger(SqliteQueryImpl.class);
	
	@Resource(name = "sqliteDataSource")  
	private DruidDataSource sqliteDataSource;
	  /**    
     * 获取数据库连接    
     * @return conn    
	 * @throws SQLException 
     */    
    private Connection getConnection() throws SQLException {    
        Connection conn = sqliteDataSource.getConnection();    
        conn.setAutoCommit(false);    
        return conn;    
    }    
    
    /**    
     * 关闭数据库连接    
     * @param conn    
     */    
    private void close(Connection conn, Statement stmt, ResultSet rs) {    
        if (null != rs) {    
            try {    
                rs.close();    
            } catch (SQLException ex) {    
                logger.error(null, ex);    
            }    
            rs = null;    
        }    
        if (null != stmt) {    
            try {    
                stmt.close();    
            } catch (SQLException ex) {    
                logger.error(null, ex);    
            }    
            stmt = null;    
        } 
        if(null != conn){
        	try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	conn = null;
        }
    }
    
    
    protected boolean isBaseClass(Class c) {
		if (c.getName().equals("java.lang.Integer")) {
			return true;
		}
		if (c.getName().equals("java.lang.Date")) {
			return true;
		}
		if (c.getName().equals("java.lang.Timestamp")) {
			return true;
		}
		if (c.getName().equals("java.lang.int")) {
			return true;
		}
		if (c.getName().equals("java.lang.String")) {
			return true;
		}
		if (c.getName().equals("java.lang.double")) {
			return true;
		}
		if (c.getName().equals("java.lang.Double")) {
			return true;
		}
		if (c.getName().equals("java.lang.long")) {
			return true;
		}
		if (c.getName().equals("java.lang.Long")) {
			return true;
		}
		return false;
	}
    
    
    @Override
    public int executeUpdate(String sql){
    	Connection conn = null;
    	Statement stmt = null;
    	int i = 0;
    	try {
			conn = this.getConnection();
			logger.info("open sqliteDB successfully");
			stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
			conn.commit();
    	} catch (SQLException e) {
    		conn.rollback();
			e.printStackTrace();
			throw new BiException("数据库异常");
		} finally{
			this.close(conn, stmt, null);
			return i;
		}
    }

	@Override
	public int executeUpdate(String sql, List paraList) {
		Connection conn = null;
		PreparedStatement stm = null;
		int count = 0;
		try {
			conn = this.getConnection();
			stm = conn.prepareStatement(sql);
			if(CollectionUtils.isNotEmpty(paraList)){
				for (int i = 0; i < paraList.size(); i++) {
					Object obj = paraList.get(i);
					stm.setObject(i+1, obj);//从1开始
				}
			}
			count = stm.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BiException("数据库异常");
		} finally{
		  	this.close(conn, stm, null);
			return count;
		}
	}

	@Override
	public List executeQuery(String sql, List paraList, Class className) {
		Connection conn = null;
		PreparedStatement stm = null;
		ResultSet rs = null;
		List list = new LinkedList();
		try {
			conn = this.getConnection();
			stm = conn.prepareStatement(sql);
			if(CollectionUtils.isNotEmpty(paraList)){
				for (int i = 0; i < paraList.size(); i++) {
					Object obj = paraList.get(i);
					stm.setObject(i+1, obj);//从1开始
				}
			}
			rs = stm.executeQuery();
			ResultSetMetaData metadata = rs.getMetaData();
			while(rs.next()){
				if(isBaseClass(className)){
					//基础数据类型 证明只查一个字段
					String data = StringUtils.EMPTY;
					Object curObj = rs.getObject(1);
					if(curObj!=null && curObj.getClass().getSimpleName().equalsIgnoreCase("timestamp")){
						//timestamp型的 特殊处理
						Timestamp timeStamp = rs.getTimestamp(1);
						data = CommonHelper.date2Str(timeStamp, CommonHelper.DF_DATE_TIME);
					}else if(curObj!=null && curObj.getClass().getSimpleName().equalsIgnoreCase("date")){
						//date型的 特殊处理
						Date date = rs.getDate(1);
						data = CommonHelper.date2Str(date, CommonHelper.DF_DATE_TIME);
					}else{
						data = rs.getString(1);
					}
					Object re = createPrimitiveObj(data,className);
					list.add(re);
				}else{
					//复杂类型
					Object obj = className.newInstance();
					for (int i = 1; i <= metadata.getColumnCount(); i++) {//遍历每列，从1开始
						String columnName = metadata.getColumnName(i);
						int type = metadata.getColumnType(i);
						
						String data = StringUtils.EMPTY;
						Object curObj = rs.getObject(1);
						if(curObj!=null && curObj.getClass().getSimpleName().equalsIgnoreCase("timestamp")){
							//timestamp型的 特殊处理
							Timestamp timeStamp = rs.getTimestamp(1);
							data = CommonHelper.date2Str(timeStamp, CommonHelper.DF_DATE_TIME);
						}else if(curObj!=null && curObj.getClass().getSimpleName().equalsIgnoreCase("date")){
							//date型的 特殊处理
							Date date = rs.getDate(1);
							data = CommonHelper.date2Str(date, CommonHelper.DF_DATE_TIME);
						}else{
							data = rs.getString(1);
						}
						setObjectColumn(obj, columnName, data, type);
					}
					list.add(obj);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BiException("数据库异常");
		} finally{
			this.close(conn, stm, rs);
			return list;
		}
	}
	/**
	 * 基础数据类型设置为复杂对象的属性
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * */
	private void setObjectColumn(Object obj, String columnName, String data, int type) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (data == null) {
			return;
		}
		Method[] methods = obj.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if(methods[i].getName().compareToIgnoreCase("set" + columnName) == 0){
				Method method = methods[i];
				Class[] cs = method.getParameterTypes();
				if (cs.length == 1) {
					String paramType = cs[0].getName();
					if(paramType.equals("java.lang.String")){
						Object[] paras = new Object[1];
						paras[0] = data;
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.lang.Integer")||paramType.equals("int")){
						Object[] paras = new Object[1];
						paras[0] = java.lang.Integer.valueOf(data).intValue();
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.lang.Long")||paramType.equals("long")){
						Object[] paras = new Object[1];
						paras[0] = java.lang.Long.valueOf(data).longValue();
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.lang.Float")||paramType.equals("float")){
						Object[] paras = new Object[1];
						paras[0] = java.lang.Float.valueOf(data).floatValue();
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.lang.Double")||paramType.equals("double")){
						Object[] paras = new Object[1];
						paras[0] = java.lang.Double.valueOf(data).doubleValue();
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.lang.Boolean")||paramType.equals("boolean")){
						Object[] paras = new Object[1];
						paras[0] = java.lang.Boolean.valueOf(data);
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.sql.Timestamp")){
						Object[] paras = new Object[1];
						paras[0] = new Timestamp(CommonHelper.str2Date(data, CommonHelper.DF_DATE_TIME).getTime());
						method.invoke(obj, paras);
						return;
					}else if(paramType.equals("java.util.Date")){
						Object[] paras = new Object[1];
						paras[0] = CommonHelper.str2Date(data, CommonHelper.DF_DATE_TIME);
						method.invoke(obj, paras);
						return;
					}
				}
			}
		}
	}
	
	/**
	 * 字符串转化为基础数据类型
	 * **/
	private Object createPrimitiveObj(String data, Class className) {
		String c = className.getName();
		if(c.equals("java.lang.Integer")||c.equals("java.lang.int")){
			return java.lang.Integer.valueOf(data).intValue();
		}else if(c.equals("java.lang.Date")){
			return CommonHelper.str2Date(data,CommonHelper.DF_DATE_TIME);
		}else if(c.equals("java.lang.Timestamp")){
			return new Timestamp(CommonHelper.str2Date(data,CommonHelper.DF_DATE_TIME).getTime());
		}else if(c.equals("java.lang.String")){
			return data;
		}else if(c.equals("java.lang.Boolean")||c.equals("java.lang.boolean")){
			return java.lang.Boolean.valueOf(data);
		}else if(c.equals("java.lang.float")||c.equals("java.lang.Float")){
			return java.lang.Float.valueOf(data).floatValue();
		}else if(c.equals("java.lang.double")||c.equals("java.lang.Double")){
			return java.lang.Double.valueOf(data).doubleValue();
		}else if(c.equals("java.lang.Long")||c.equals("java.lang.long")){
			return Long.parseLong(data);
		}
		return null;
	}
}
