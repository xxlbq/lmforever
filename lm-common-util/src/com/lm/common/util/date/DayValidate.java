package com.lm.common.util.date;

import java.util.regex.Pattern;

public class DayValidate {

	
	private static int  year;
	private static int  month;
	private static int  day;
	
	public DayValidate() {
		
	/*	java.util.GregorianCalendar currentDate = new java.util.GregorianCalendar();
		 year = currentDate.get(java.util.Calendar.YEAR);
		 month = currentDate.get(java.util.Calendar.MONTH) + 1;
		 day = currentDate.get(java.util.Calendar.DAY_OF_MONTH);*/
	}
	private static boolean isLeap(int year){ 
		if((year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0))
		return true;
		else
	    return false;
	}
	public static boolean isDate(String date){ 
		boolean b=false;
		if(!date.startsWith("0")&&Pattern.matches("^[1|2][0-9][0-9][0-9][0|1][0-9][0-3][0-9]$",date)){ 
			 year=Integer.parseInt(date.substring(0,4));
			 month=Integer.parseInt(date.substring(4,6));
			 day=Integer.parseInt(date.substring(6,8));
			 if(month>0&&month<=12){ 
				 int maxday=getDate(year,month);
				 if(day>0&&day<=maxday){ 
					 b=true;
				 }
			 }
		}
		return b;
	}
	public static boolean isTime(String time){ 
		boolean b=false;
		if(Pattern.matches("^[0-9][0-9]*$",time)){ 
			 int hour=Integer.parseInt(time.substring(0,2));
			 int minute=Integer.parseInt(time.substring(2,4));
			// if(hour>0&&hour<=60&&minute>=0&&minute<60){ 
				 if(hour>=0&&hour<=24&&minute>=0&&minute<60){ 
				 	 b=true;
			 }
		}
		return b;
	}
	public static boolean isDateLimit(String date1,String date2){ 
		boolean b=false;
		if(date1!=null&&date2!=null&&!date1.trim().equals("")&&!date2.trim().equals("")){ 
			if(date1.compareTo(date2)<=0){ 
				b=true;
			}
		}
		return b;
		
	}
	public static int getDate(int year,int month)
	{ 
		int ar[]= new int[12];
		ar[0]=31;
		ar[1]=isLeap(year)?29:28;
		ar[2]=31;
		ar[3]=30;
		ar[4]=31;
		ar[5]=30;
		ar[6]=31;
		ar[7]=31;
		ar[8]=30;
		ar[9]=31;
		ar[10]=30;
		ar[11]=31;
		return ar[month-1];
	}


	/**
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param i
	 */
	public void setDay(int i) {
		day = i;
	}

	/**
	 * @param i
	 */
	public void setMonth(int i) {
		month = i;
	}

	/**
	 * @param i
	 */
	public void setYear(int i) {
		year = i;
	}

	
//	public static void main(String[] args) {
//		DayValidate dv=new DayValidate();
//		System.out.println(dv.isDate("20080808"));
//		
//	}
}
