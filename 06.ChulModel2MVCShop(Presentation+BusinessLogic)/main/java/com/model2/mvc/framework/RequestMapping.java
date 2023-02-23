package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class RequestMapping {
	
	///Field
	private static RequestMapping requestMapping;
	private Map<String, Action> map;
	private Properties properties;
	
	///Constructor
	private RequestMapping(String resources) {
		
		map = new HashMap<String, Action>();
		
		InputStream in = null;
		try{
			in = getClass().getClassLoader().getResourceAsStream(resources);
			properties = new Properties();
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("RequestMapping.java actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ 
					in.close(); 
				} catch(Exception ex){ }
			}
		}
	} 
	
	///Method
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	public Action getAction(String path){
		
		Action action = map.get(path);
		
		if(action == null){
			
			String className = properties.getProperty(path);
			System.out.println("RequestMapping.java properties= " + properties);
			System.out.println("RequestMapping.java path= " + path);			
			System.out.println("RequestMapping.java className= " + className);
			className = className.trim();
			
			try{
				Class c = Class.forName(className);
				Object obj = c.newInstance();
				if(obj instanceof Action){
					map.put(path, (Action)obj);
					action = (Action)obj;
				}else{
					throw new ClassCastException("RequestMapping.java Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("RequestMapping.java Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}