package com.lm.common.util.obj;


public class ObjectCommonUtil {
	
//	private transient  static Logger logger = 
//		Logger.getLogger(ObjectCommonUtil.class);
	
    public static boolean isEmpty(Object obj){
    	return obj == null ? true :false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !(isEmpty(obj));
    }
    
    public static boolean isNotEmpty(int param) {
    	return !(isEmpty(param));
    }
}
