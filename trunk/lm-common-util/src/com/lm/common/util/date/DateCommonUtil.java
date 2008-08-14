package com.lm.common.util.date;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import com.lm.common.util.str.StringCommonUtil;

public class DateCommonUtil {
	
	public static Date parseDate(String dateStr, String pattern) {
		
		if( StringCommonUtil.isEmpty(dateStr))
			return null;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);  
        return formatter.parse(dateStr, pos);
    }
	
	
//    public static String moveDatePrev(String dateFormatString){
//        SimpleDateFormat   df = new  SimpleDateFormat(DATE_PATTERN_8); 
//        Calendar calendar =Calendar.getInstance();  
//        
//        Date d = getStringDateTime(dateFormatString,DATE_PATTERN_8);
//        System.out.println(d);
//        calendar.setTime(d );
//        
//        calendar.roll(Calendar.DAY_OF_YEAR,-1);
//        
//        return df.format(calendar.getTime());
//    }
	
	
	public static String getDateAsString(Date date,String format){
		String dtString;
		SimpleDateFormat sdt=new SimpleDateFormat(format);
		dtString=sdt.format(new Date());
		return dtString;
	}
	
	public static String getDateAsMMddHHmmss(Date date){
		String dtString;
		SimpleDateFormat sdt=new SimpleDateFormat("ddHHmmss");
		dtString=sdt.format(new Date());
		return dtString;
	}
	
	public static String getT_GdraftDate(Date date){
		String dtString;
		SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd");
		dtString=sdt.format(new Date());
		return dtString;
	}
	public static int getHHmmTerm(String start,String end){
		int term;
		int startHH;
		int startmm;
		int endHH;
		int endmm;
		
		startHH=Integer.parseInt(start.substring(0,2));
		startmm=Integer.parseInt(start.substring(2,4));
		endHH=Integer.parseInt(end.substring(0,2));
		endmm=Integer.parseInt(end.substring(2,4));
		
		term=(endHH - startHH)*60+(endmm - startmm);
		return term;
	}
	
	public static String getTermAsHour(String term){
		double hours=00.00;
		double m=60.00;
		hours=Integer.parseInt(term)/m;
		
	    DecimalFormat df = new DecimalFormat("###,###.##");
	    

		return String.valueOf(df.format(hours));
	}
	
	public static String getYYMMDate(String dateStr){
		String yymmStr;
		if(dateStr==null||dateStr.equals("")||dateStr.length()!=8){
			yymmStr="";
		}else{
			yymmStr=dateStr.substring(0,6);
		}
		return yymmStr;
	}
	
	public static Calendar getCalendar(String yyyyMMddStr){
		if(DayValidate.isDate(yyyyMMddStr)){ 
			int year=Integer.parseInt(yyyyMMddStr.substring(0,4));
			int month=Integer.parseInt(yyyyMMddStr.substring(4,6))-1;
			int day=Integer.parseInt(yyyyMMddStr.substring(6,8));
			return new GregorianCalendar(year,month,day);
			
		}else{ 
			return null;
		}
		
	}
	
	/**
	 * @author xxlbq
	 * @add-time    2006/09/10
	 */
	public static String getyyyymmddStringFromCalendarObject(Calendar cal){
		
		return 	cal == null ? "" :getDateAsStringYYYYMMDD(cal.getTime());
	}
	
	/**
	 * @author xxlbq
	 * @add-time    2006/09/10
	 */
	public static String getDateAsStringYYYYMMDD(Date date){
		String dtString;
		SimpleDateFormat sdt=new SimpleDateFormat("yyyyMMdd");
		dtString=sdt.format(new Date());
		return dtString;
	}


	
	public static void main(String[] args) {
//		System.out.println(GetDate.getDateAsMMddHHmmss(new Date()));
//		Date d = new Date();
//		Date d = new Date(1172338553984L);
		
//		d.setTime(1172338553984L);
//		Calendar c = Calendar.getInstance();
//		c.setTimeInMillis(1172338553984L);
//		System.out.println(c.SECOND);
		
//		d.setTime(1172338553984L);
//		System.out.println(d.getHours()+":"+d.getMinutes()+":"+d.getSeconds());
//		System.out.println(d.getTime());//1172338553984
//		
//		1172338157437:20070225012917
//		System.out.println(DateUtil.getDateStringAsYYYYMMddHHmmss(d));//20070225013554
		
		System.out.println(DateUtil.getDateStringAsYYYYMMddHHmmss(1172338553984L));
		
	}
//	public static void main(String[] args) {
//		System.out.println(GetDate.getT_GdraftDate(new Date()));
//	}
//	public static void main(String[] args) {
//		System.out.println(GetDate.getDateAsString(new Date()));
//	}
//	public static void main(String[] args) {
//		int test;
//		test=GetDate.getHHmmTerm("2250","2310");
//		System.out.println(test);
//	}
	
//	public static void main(String[] args) {
//		System.out.println(getTermAsHour("61"));
//	}

}
