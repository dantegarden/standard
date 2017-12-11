package com.dvt.standard.commons.utils;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class XmlRpcUtils {
	
	private static XmlRpcClientConfigImpl config;  
	private static XmlRpcClient client;  
	
	private static final void init(){
		if(config==null){
			config = new XmlRpcClientConfigImpl();
		}
		if(client==null){
			client = new XmlRpcClient();
		}
	}
	
	public static List<Object> getXMLRPCList(String url,String pMethodName, List pParams){
		init();
        try {
        	config.setServerURL(new URL(url));  
        	client.setConfig(config);
			List<Object> partners = Arrays.asList((Object[])client.execute(pMethodName, pParams));
			return partners;
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
	} 
	
	public static Object getXMLRPC(String url,String pMethodName, List pParams){
		init();
        try {
        	config.setServerURL(new URL(url));  
        	client.setConfig(config);
			Object partners = client.execute(pMethodName, pParams);
			return partners;
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
	} 
}
