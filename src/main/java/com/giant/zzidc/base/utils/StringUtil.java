/**
 * [说明/描述]
 * @author likai
 * @date 2018年8月6日 下午6:50:25
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
package com.giant.zzidc.base.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * [说明/描述]
 * @author likai
 * @date 2018年8月6日 下午6:50:25
 * @company Gainet
 * @version 1.0
 * @copyright copyright (c) 2018
 */
/**
 * @author likai
 *
 */
public class StringUtil {
	public static final String SHORT_FORMATTER = "yyyy-MM-dd";
	public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
	public static final String LENGT_FORMATTER = "yyyy-MM-dd HH:mm:ss.sss";

	public static String dateToString(Date date, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(date);
	}

	public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER);
        return sdf.format(date);
	}

	public static String timestampToString(Timestamp date, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(date);
	}

	public static String timestampToString(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATTER);
        return sdf.format(date);
	}
}
