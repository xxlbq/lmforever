package com.lubq.lm.util;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import com.lm.common.util.str.StringCommonUtil;

public class StringUtil {
	
	public static List<String> splitString(String str,String dem){
		if(StringCommonUtil.isEmpty(str)){
			System.out.println("str  is  null .. return empty list");
			return new ArrayList<String>();
		}
		
		System.out.println("Sring :["+str+"], dem:"+dem);
		return Arrays.asList(str.split(dem));
	}

	
	public static String customerStringCheck(String customerId){
		if(StringCommonUtil.isEmpty(customerId)){
			System.out.println("customerId is empty !");
			return null; 
		}
		
		customerId = customerId.trim();
		
		if(customerId.length() != 8){
			System.out.println("customerId is not 8 long !");
			return null; 
		}
		
		return customerId ;
		
	}
	
	public static List<String> customerStringListCheck(List<String> customerIdList){
		List<String> cusList = new ArrayList<String>();
		for (String cus : customerIdList) {
			cus = customerStringCheck(cus);
			if(StringCommonUtil.isNotEmpty( cus )){
				cusList.add(cus);
			}
		}
		return cusList ;
	}
	

	
	
	
	
	
	
	
	public static void main(String[] args) {
//		List< String> list = StringUtil.splitString("i am a man.\n you are a not man \n,pig .", "\n");
		List< String> list = StringUtil.splitString("\r\n \r\n", "\r\n");
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	
	
	
}
