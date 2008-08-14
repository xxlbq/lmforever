package com.lm.common.util.date;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class DateUtil {
	
	public static String getDateStringAsYYYYMMddHHmmss(Date date){
		SimpleDateFormat sdt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdt.format(date);
	}
	
	public static String getDateStringAsYYYYMMddHHmmss(long time){
		Date d = new Date();
		d.setTime(time);
		return DateUtil.getDateStringAsYYYYMMddHHmmss(d);
	}
	
	public static Calendar getTestCalendar(String yyyyMMddStr){
		
		if(DayValidate.isDate(yyyyMMddStr)){ 
			
			int year=Integer.parseInt(yyyyMMddStr.substring(0,4));
			int month=Integer.parseInt(yyyyMMddStr.substring(4,6))-1;
			int day=Integer.parseInt(yyyyMMddStr.substring(6,8));
			return new GregorianCalendar(year,month,day);
			
		}else{ 
			return null;
		}
		
	}
	
	public static boolean isNumericDateIfExist(String date){
		
		
		if(date.length()!= 8) return false;
		
		return	Pattern.matches("^[1|2][0-9][0-9][0-9][0|1][0-9][0-3][0-9]$",date);
		
	}
	
	public static boolean isMonth(int month){
		
		return month >= 1 && month <= 12 ;
	}
	
	public static boolean isDay(int year,int month,int day) {
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(year,month-1,1);
        return calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)>=day;
	}
	
	public static boolean isDateYYYYMMDD(String yyyyMMdd){
		
		
		
		int year=Integer.parseInt(yyyyMMdd.substring(0,4));
		int month=Integer.parseInt(yyyyMMdd.substring(4,6));
		int day=Integer.parseInt(yyyyMMdd.substring(6,8));
		
		if ( !isMonth(month)) return false;
		
		if ( !isDay(year,month,day)) return false;
		
		return true;
	}
	
//	public static String getDateFromString(String formatString,String dateString){
//		
//		Format format = new SimpleDateFormat(formatString);
//		format.parseObject(dateString)
//	}
//	
//	
//	 8 
//	10 
//	11         Date date = new Date();
//	12         System.out.println(format.format(date));
//	13 
//	14         try {
//	15             Date date1 = (Date) format.parseObject("2005-11-15 20:55:48");
//	16             System.out.println(date1);
//	17         } catch (ParseException e) {
//	18             // TODO Auto-generated catch block
//	19 
//	20         }
//	21 
//	22         format = new SimpleDateFormat("yyyy-MM-dd");
//	23         System.out.println(format.format(date));
//	24         format = new SimpleDateFormat("hh:mm:ss");
//	25         System.out.println(format.format(new Date()));
//	26 
//	27     }
	
	
	public static void main(String[] args) {
		
	}


}
