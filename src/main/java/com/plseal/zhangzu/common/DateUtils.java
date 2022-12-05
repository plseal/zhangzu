package com.plseal.zhangzu.common;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateUtils {

    // date型をString型に転換
 	// formatTypeはyyyy-MM-dd HH:mm:ss
 	public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }
    
    // long型をString型に転換
 	// formatTypeはyyyy-MM-dd HH:mm:ss
 	public static String longToString(long currentTime, String formatType){
		String strTime = "";
		try {
			Date date = longToDate(currentTime, formatType); // long型をDate型に転換
			strTime = dateToString(date, formatType); // Date型をString型に転換
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return strTime;
 	}

	// string型をlong型に転換
 	// formatTypeはyyyy-MM-dd HH:mm:ss
 	public static long stringToLong(String strTime, String formatType){

        Date date = stringToDate(strTime, formatType); // String型をDate型に転換
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date型をlong型に転換
            return currentTime;
        }
    }

	// long型をDate型に転換
 	// formatTypeはyyyy-MM-dd HH:mm:ss
 	public static Date longToDate(long currentTime, String formatType)
 			throws ParseException {
 		Date dateOld = new Date(currentTime); 
 		String sDateTime = dateToString(dateOld, formatType); 
 		Date date = stringToDate(sDateTime, formatType); 
 		return date;
 	}

	//string型をDate型に転換
	// formatTypeはyyyy-MM-dd HH:mm:ss
 	public static Date stringToDate(String strTime, String formatType) {
 		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
 		Date date = null;
         try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
 		return date;
     }
	
	// Date型をLong型に転換
    public static long dateToLong(Date date) {
        return date.getTime();  
    }
	
	// yyyy-MM-ddからyyyy年MM月dd日に転換
    public static String dateFormatForMailTitle(Calendar cl) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		
		return sdf.format(cl.getTime());
	}


}