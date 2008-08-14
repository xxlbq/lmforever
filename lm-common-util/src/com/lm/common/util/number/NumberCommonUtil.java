package com.lm.common.util.number;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberCommonUtil {
	
	
	public static 
	String decimalFormat(BigDecimal price,NumberFormat nf){
		
		String formatString = null;
		formatString = nf.format(price.longValue());
	    return formatString;
	}

}
