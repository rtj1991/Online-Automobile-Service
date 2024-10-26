package com.online.automobile.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

public class UtilManager {

    private static final SimpleDateFormat format_sql_full_date = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat year_month = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###.00");
    private static final SimpleDateFormat day = new SimpleDateFormat("EEEE");
    private static final DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat sourceFormatSql = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat sourceFormatSql2 = new SimpleDateFormat("MM/dd/YY");
    private static final DateFormat sourceFormatSql1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat formatetimestamp = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat timestampDateOnly = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df2 = new DecimalFormat(".##");


    public static String getCurrentMonth() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.MONTH));
    }

    public static String getCurrentYear() {
        Calendar now = Calendar.getInstance();
        return String.valueOf(now.get(Calendar.YEAR));
    }

    public static String getDay(Date yyyymmdd) {
        try {
            return day.format(yyyymmdd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

    public static String formatDate(Date date) {
        try {
            return sourceFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatDate(String date) {
        try {
            return sourceFormatSql.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }
    public static Date formatDate2(String date) {
        try {
            return sourceFormatSql2.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date formatDate1(String date) {
        try {
            return sourceFormatSql1.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }


    public static Date formatTime(String date) {
        try {
            return formatetimestamp.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formatTimestampDate(Date date) {
        try {
            return timestampDateOnly.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

    public static String md5(String input) {
        try {
            String result = input;
            if (input != null) {
                MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
                md.update(input.getBytes());
                BigInteger hash = new BigInteger(1, md.digest());
                result = hash.toString(16);
                while (result.length() < 32) { //40 for SHA-1
                    result = "0" + result;
                }
            }
            return result;
        } catch (NoSuchAlgorithmException ex) {
        }
        return "";
    }

    public static String formatDouble(double number) {
        try {
            return DECIMAL_FORMAT.format(number);
        } catch (Exception e) {
            return Double.toString(number);
        }
    }

}
