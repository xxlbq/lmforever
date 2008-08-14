package com.lm.common.util.str;


public class StringCommonUtil {
	
//	private transient  static Logger logger = 
//		Logger.getLogger(StringCommonUtil.class);
	
	
	
    public static boolean isEmpty(String param) {
        return ( null == param || param.length() < 1);
    }
    
    
    public static boolean isNotEmpty(String param) {
        return !(isEmpty(param));
    }
    
    
}
