package com.lubq.lm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日付、時間の変換等に用いるツールクラス
 * 
 * @author wenyi <wenyi@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * 
 * @version $Id: DatetimeUtil.java,v 1.7 2007/04/14 07:56:37 liyan Exp $
 */
public class DatetimeUtil {

    public static final String ShowDateFormat = "yyyy-MM-dd";

    public static final String ServerDateFormat = "yyyyMMdd";

    public static final String m_timeFormatHHmm = "HHmm";

    /**
     * 日付のフォーマット化�w��t�H���ɂ���
     * 
     * @param p_date_s1��
     * @param p_format1��b�g
     * @param p_format2��b�g
     * @return String
     */
    public static String datestringTodDatestring(String p_date_s1,
            String p_format1, String p_format2) throws ParseException {

        return dateToString(stringToDate(p_date_s1, p_format1), p_format2);
    }

    /**
     * 日付のフォーマット化�����
     * 
     * @param p_date
     *            Date
     * @param p_format
     *            String
     * @return String
     */
    public static String dateToString(Date p_date, String p_format) {
        SimpleDateFormat formatter = new SimpleDateFormat(p_format);
        return formatter.format(p_date);
    }

    /**
     * 日付のフォーマット化�����
     * 
     * @param p_date
     *            Date
     * @param p_format
     *            String
     * @return String
     */
    public static Date stringToDate(String p_date_s, String p_format)
            throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat(p_format);

        return formatter.parse(p_date_s);

    }

    /**
     * 日付のフォーマット化
     * 
     * @param origDate
     * @param interval(interval >
     *            0: next date; interval < 0: previeues date)
     * @return
     */
    public static Date getRelativeDate(Date origDate, int interval) {

        Calendar inst = Calendar.getInstance();
        inst.setTime(origDate);

        inst.add(Calendar.DATE, interval);

        return inst.getTime();
    }

}
