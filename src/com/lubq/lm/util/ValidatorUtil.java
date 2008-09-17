package com.lubq.lm.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;

import cn.bestwiz.jhf.core.util.LogUtil;

/**
 * ValidatorUtil Class. 校验用Util类
 * 
 * @author JHF Team <jhf@bestwiz.cn>
 * 
 * 
 * @copyright 2006-2007, BestWiz(Dalian) Co.,Ltd
 */
public class ValidatorUtil implements Serializable {

    private static final long serialVersionUID = -5983684177044153269L;

    protected transient static final Log logger = LogUtil.getLog(ValidatorUtil.class);

    private static final String DEFAULT_ENCODING = "MS932";

    /**
     * 电话番号判断
     * 
     * @param value
     * @return boolean
     */
    public static boolean isTelephone(String value) {

        boolean bReturn = false;

        if (value != null && StringUtils.isNotEmpty(value) && value.length() == 10) {
            String[] tels = value.split("-");
            if (tels != null && tels.length == 3 && ValidatorUtil.isHalfWidthDigit(tels[0]) && tels[0].length() >= 1
                    && ValidatorUtil.isHalfWidthDigit(tels[1]) && tels[1].length() >= 1
                    && ValidatorUtil.isHalfWidthDigit(tels[2]) && tels[2].length() >= 1) {
                bReturn = true;
            }
        }

        return bReturn;
    }

    /**
     * Email判断
     * 
     * @param Str
     * @return
     */
    public static boolean isEmail(String str) {

        if (StringUtils.isBlank(str)) {
            return false;
        }

        if (str.startsWith(".")) {
            return false;
        }

        String[] arr = str.split("@");

        if (arr.length != 2) {
            return false;
        }

        String account = arr[0];
        String mailSuffix = arr[1];

        account = account.replaceAll("\\.", "");

        str = account + "@" + mailSuffix;

        return GenericValidator.isEmail(str);
    }

    /**
     * 日期判断
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isValidDate(int year, int month, int day) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.set(GregorianCalendar.YEAR, year);
        gc.set(GregorianCalendar.MONTH, month - 1);
        gc.set(GregorianCalendar.DATE, day);
        gc.setLenient(false);
        try {
            gc.get(GregorianCalendar.DATE);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 日期格式及有效性判断
     * 
     * @param str
     *            日期字符串
     * @param format
     *            所依据的格式
     * @return boolean 正确有效返回true，若格式正确但内容无效，则返回false
     * @throws ParseException
     *             若格式错误，则抛出此异常。
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public static boolean isValidDateFormat(String str, String format) throws ParseException {

        if (GenericValidator.isBlankOrNull(str) || GenericValidator.isBlankOrNull(format)) {
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(true);

        Date date = sdf.parse(str);
        String str2 = sdf.format(date);

        if (!str.equals(str2)) {
            return false;
        }

        return true;
    }

    /**
     * 半角数字判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthDigit(char c) // '0' ~ '9'
    {

        return (c >= 0x0030 && c <= 0x0039);
    }

    /**
     * 半角数字判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthDigit(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角小写字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthAlphaLower(char c) // a~z
    {

        return (c >= 0x0061 && c <= 0x007A);
    }

    /**
     * 半角小写字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthAlphaLower(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthAlphaLower(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角数字判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthDigit(char c) {

        return (c >= 0xFF10 && c <= 0xFF19);
    }

    /**
     * 全角数字判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthDigit(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角空格判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthSpace(char c) {

        return c == 0x3000;
    }

    /**
     * 全角空格判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthSpace(String s) {

        if (s == null) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthSpace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角空格判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthSpace(char c) {

        return c == 0x0020;
    }

    /**
     * 半角空格判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthSpace(String s) {

        if (s == null) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthSpace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 汉字判断
     * 
     * @param c
     * @return
     */
    public static boolean isKanji(char c) {

        return (c >= 0x2E80 && c <= 0x2EFF)

        || (c >= 0x2F00 && c <= 0x2FDF) || (c >= 0x3100 && c <= 0x312F) || (c >= 0x31A0 && c <= 0x31BF)

        || (c >= 0x3400 && c <= 0x4DBF)

        || (c >= 0x4E00 && c <= 0x9FAF)

        || (c >= 0xf900 && c <= 0xFAFF);

    }

    /**
     * 汉字判断
     * 
     * @param s
     * @return
     */
    public static boolean isKanji(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isKanji(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 平假名判断
     * 
     * @param c
     * @return
     */
    public static boolean isHiragana(char c) {

        return (c >= 0x3040 && c <= 0x309F);
    }

    /**
     * 平假名判断
     * 
     * @param s
     * @return
     */
    public static boolean isHiragana(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHiragana(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角片假名判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthHirahiramei(char c) {

        return (c >= 0xff61 && c <= 0xff9f);
    }

    /**
     * 半角片假名判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthHirahiramei(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthHirahiramei(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角片假名判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthHirahiramei(char c) {

        return (c >= 0x30a0 && c <= 0x30ff);
    }

    /**
     * 全角片假名判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthHirahiramei(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthHirahiramei(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拉丁文判断
     * 
     * @param c
     * @return
     */
    public static boolean isLatin(char c) {

        return (c >= 0x0020 && c <= 0x00A0);
    }

    /**
     * 拉丁文判断
     * 
     * @param s
     * @return
     */
    public static boolean isLatin(String s) {

        if (StringUtils.isBlank(s)) {
            return false;
        }

        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isLatin(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 空格判断
     * 
     * @param c
     * @return
     */
    public static boolean isSpace(char c) {

        return isHalfWidthSpace(c) || isFullWidthSpace(c);
    }

    /**
     * 空格判断
     * 
     * @param str
     * @return
     */
    public static boolean isSpace(String str) {

        if (str == null) {
            return false;
        }
        for (int i = 0, len = str.length(); i < len; i++) {
            if (!isSpace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数字判断
     * 
     * @param c
     * @return
     */
    public static boolean isDigit(char c) {

        return isHalfWidthDigit(c) || isFullWidthDigit(c);
    }

    /**
     * 数字判断
     * 
     * @param str
     * @return
     */
    public static boolean isDigit(String str) {

        if (GenericValidator.isBlankOrNull(str)) {
            return false;
        }
        for (int i = 0, len = str.length(); i < len; i++) {
            if (!isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthAlpha(char c) // A~Z || a~z
    {

        return isHalfWidthAlphaLower(c) || isHalfWidthAlphaUpper(c);
    }

    /**
     * 半角字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthAlpha(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthAlpha(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角英文或数字判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthDigitorAlpha(char c) {

        return isHalfWidthDigit(c) || isHalfWidthAlpha(c);
    }

    /**
     * 半角英文或数字
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthDigitorAlpha(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!(isHalfWidthDigitorAlpha(s.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角大写字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidthAlphaUpper(char c) // A~Z
    {

        return (c >= 0x0041 && c <= 0x005A);
    }

    /**
     * 半角大写字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidthAlphaUpper(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidthAlphaUpper(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthAlpha(char c) {

        return isFullWidthAlphaLower(c) || isFullWidthAlphaUpper(c);
    }

    /**
     * 全角字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthAlpha(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthAlpha(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角小写字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthAlphaLower(char c) {

        return (c >= 0xFF41 && c <= 0xFF5A);
    }

    /**
     * 全角小写字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthAlphaLower(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthAlphaLower(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角大写字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthAlphaUpper(char c) {

        return (c >= 0xFF21 && c <= 0xFF3A);
    }

    /**
     * 全角大写字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthAlphaUpper(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthAlphaUpper(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字母判断
     * 
     * @param c
     * @return
     */
    public static boolean isAlpha(char c) {

        return isHalfWidthAlpha(c) || isFullWidthAlpha(c);
    }

    /**
     * 字母判断
     * 
     * @param s
     * @return
     */
    public static boolean isAlpha(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isAlpha(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 片假名判断
     * 
     * @param c
     * @return
     */
    public static boolean isHirahiramei(char c) {

        return isHalfWidthHirahiramei(c) || isFullWidthHirahiramei(c);
    }

    /**
     * 片假名判断
     * 
     * @param s
     * @return
     */
    public static boolean isHirahiramei(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHirahiramei(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角假名判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidthKana(char c) {

        return isHiragana(c) || isFullWidthHirahiramei(c);
    }

    /**
     * 全角假名判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidthKana(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isFullWidthKana(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角文字判断
     * 
     * @param c
     * @return
     */
    public static boolean isHalfWidth(char c) {

        return checkLength(c + "") == 1;
    }

    /**
     * 半角文字判断
     * 
     * @param s
     * @return
     */
    public static boolean isHalfWidth(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!isHalfWidth(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全角文字判断
     * 
     * @param c
     * @return
     */
    public static boolean isFullWidth(char c) {

        return !isHalfWidth(c);
    }

    /**
     * 全角文字判断
     * 
     * @param s
     * @return
     */
    public static boolean isFullWidth(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (isHalfWidth(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * URL地址判断
     * 
     * @param s
     * @return
     */
    public static boolean isURL(String s) {

        if (GenericValidator.isBlankOrNull(s)) {
            return false;
        }
        Pattern p = Pattern.compile("^http://.+..+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 按字节计算字符串的长度
     * 
     * @param str
     *            输入字符串
     * @return int 字符串的字节长度
     * @throws UnsupportedEncodingException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public static int checkLength(String str) {

        int length;

        try {
            length = str.getBytes(DEFAULT_ENCODING).length;
        } catch (UnsupportedEncodingException e) {
            logger.error(e, e);
            length = str.getBytes().length;
        }

        return length;
    }

    /**
     * 特殊字符校验
     * 
     * @param s
     * @return
     */
    public static boolean checkSpecialChar(String s) {

        String standandString = "~`!#$%^&*<>?=";
        boolean result = true;
        if (s == null) {
            return result;
        }
        for (int i = 0; i < s.length(); i++) {
            if (standandString.indexOf(s.charAt(i)) >= 0) {
                return result;
            }
        }
        return false;
    }

    /**
     * 输入的字符串是否为正数(包括正小数)或0 输入字符串-0按0处理
     * 
     * @param numStr
     * @return
     */
    public static boolean isPlusFraction(String numStr) {

        boolean isPs = false;

        try {

            BigDecimal numFt = new BigDecimal(numStr);

            if (numFt.compareTo(BigDecimal.ZERO) >= 0) {
                isPs = true;
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return isPs;
    }

    /**
     * The function for check mobile mail address
     * 
     * @param string
     * @return boolean
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public static boolean isMobileMail(String string) {

        boolean flag = false;
        String str = string.toLowerCase();

        if (isEmail(str)) {
            String mailSuffix = str.split("@")[1];

            String mailaddr = "docomo\\.ne\\.jp/emnet\\.ne\\.jp/ezweb\\.ne\\.jp/.{2}?\\.ezweb\\.ne\\.jp/softbank\\.ne\\.jp/d\\.vodafone\\.ne\\.jp/h\\.vodafone\\.ne\\.jp/t\\.vodafone\\.ne\\.jp/c\\.vodafone\\.ne\\.jp/r\\.vodafone\\.ne\\.jp/k\\.vodafone\\.ne\\.jp/n\\.vodafone\\.ne\\.jp/s\\.vodafone\\.ne\\.jp/q\\.vodafone\\.ne\\.jp/ez.{1}?\\.ido\\.ne\\.jp/pdx\\.ne\\.jp/.{2}?\\.pdx\\.ne\\.jp/i\\.softbank\\.jp";

            String[] mailaddrs = mailaddr.split("/");

            for (int i = 0; i < mailaddrs.length; i++) {

                Pattern pattern = Pattern.compile(mailaddrs[i], Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(mailSuffix);
                if (matcher.matches()) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
