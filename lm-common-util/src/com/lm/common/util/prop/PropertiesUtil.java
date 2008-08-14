package com.lm.common.util.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.lm.common.util.str.StringCommonUtil;




public class PropertiesUtil {
	
	private Logger log = Logger.getLogger(PropertiesUtil.class); 
	

	private Configuration config ;
	/**
	 * full path of property file 
	 *      ==> d:\abc\efg.properties  
	 */
	private String propFullPath ;
	
	public PropertiesUtil(){}
	
	public PropertiesUtil(String propFullPath){
		
		this.propFullPath = propFullPath;
	}

	public String getPropFullPath() {
		return propFullPath;
	}

	public void setPropFullPath(String propFullPath) {
		this.propFullPath = propFullPath;
	}

	
	public void load(){
		
		PropertyConfigurator.configure("E:\\java\\EclipseWorkSpace20080201\\lm-common-util\\src\\log4j.properties");
		if(StringCommonUtil.isEmpty(propFullPath)){
			log.error("init Error : properties path is Null ! ");
		}
		try {
			config = new PropertiesConfiguration(propFullPath);
		} catch (ConfigurationException e) {
			log.error("properties ["+propFullPath+"]init Error ! ", e);
		}
		if( null == config){
			System.out.println(propFullPath);
			log.error("init Error : properties path is Null : " +propFullPath);
		}
	}
	
	public String getStringValue(String key){
		return config.getString(key);
	}
	
	public int getIntValue(String key){
		return config.getInt(key);
	}
	
	
	
//	public static String getPropertiesWithResourceBundle(String propertyKey)
//	throws Exception{
//
//		ResourceBundle resource = 
//			ResourceBundle.getBundle(Constants.SPR_CONFIG1_NAME);
//    	
//    	return getPropertyValueFromResource(resource,propertyKey);
//    	
//    }
//	
//	public static String getPropertiesWithResourceBundle(String propertyBundle, String propertyKey)
//			throws Exception{
//		
//		ResourceBundle resource = ResourceBundle.getBundle(propertyBundle);
//		
//		return getPropertyValueFromResource(resource,propertyKey);
//    }
//	
	
//	/**
//	 * @param key
//	 * @return
//	 * @throws IOException
//	 * 
//	 * "/"-separated names; leading "/" indicates absolute names;
//	 *  all other names are relative to the class's package 
//	 */
//	public static String getPropertiesWithClassInputStream(String key) throws IOException{
//    	
//    	InputStream fis=PropertiesUtil.class.getClass().getResourceAsStream(Constants.SPR_CONFIG1_FULL_NAME);
//
//    	return getPropertyValueFromInputStream(fis,key);
//	}
//	
//	public static String getPropertiesWithClassInputStream(String propertyBundle,String key) throws IOException{
//    	
//    	InputStream fis=PropertiesUtil.class.getClass().getResourceAsStream(propertyBundle);
//
//    	return getPropertyValueFromInputStream(fis,key);
//	}
//	
	
//	/**
//	 * @param key
//	 * @return
//	 * @throws IOException
//	 * 
//	 * "/"-separated names; no leading "/" (all names are absolute)
//	 */
//	public static String getPropertiesWithClassLoaderInputStream(String key) throws IOException{
//	
//    	InputStream fis=PropertiesUtil.class.getClassLoader().getResourceAsStream(Constants.SPR_CONFIG1_FULL_NAME_NO_LEAD);
//
//    	return getPropertyValueFromInputStream(fis,key);
//	}
//	
//
//	public static String getPropertiesWithClassLoaderInputStream(String propertyBundle,String key) throws IOException{
//		
//		InputStream fis=PropertiesUtil.class.getClassLoader().getResourceAsStream(propertyBundle);
//		
//		return getPropertyValueFromInputStream(fis,key);
//	}
//	
//	
	
//	public static String getPropertyValueFromResource(ResourceBundle resource,String propertyKey ){
//
//		
//		Enumeration enums = resource.getKeys();
//    	while (enums.hasMoreElements()) {
//			String key = (String) enums.nextElement();
//			prop.put(key, resource.getString(key));
//		}
//    	
//    	if(p.getProperty(propertyKey)!=null){
//    		return prop.getProperty(propertyKey);
//    	}
//    	
//    	return Constants.SPR_OBJECT_NULL;
//	}
//	
//	public static String getPropertyValueFromInputStream(InputStream fis,String key) throws IOException{
//		
//		Properties p = new Properties() ;
//		p.load(fis);
//    	fis.close();
//    	return  p.getProperty(key);
//	}
	
	
	public static void main(String[] args) throws Exception {
//		System.out.println(PropertiesUtil.getPropertiesWithResourceBundle("test1.excel.file.name"));
//		System.out.println(PropertiesUtil.getPropertiesWithResourceBundle("util.test-util-config","test"));
		
//		System.out.println(PropertiesUtil.getPropertiesWithClassInputStream("test1.excel.file.name"));
//		System.out.println(PropertiesUtil.getPropertiesWithClassInputStream("/util/test-util-config.properties","test"));
		
//		System.out.println(PropertiesUtil.getPropertiesWithClassLoaderInputStream("test1.excel.file.name"));
//		System.out.println(PropertiesUtil.getPropertiesWithClassLoaderInputStream("util/test-util-config.properties","test"));
		
		PropertiesUtil pu = new PropertiesUtil("E:\\java\\EclipseWorkSpace20080201\\lm-common-util\\src\\test.properties");
		pu.load();
		System.out.println(pu.getStringValue("app.writer"));
		
		
	}
}
