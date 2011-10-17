/**
 * 
 */
package br.com.calculofacil.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class StringUtils {

	public static final String DECIMAL_FORMAT = "#,##0.00";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	/**
	 * @param dateStr
	 * @return
	 */
	public static boolean isEmpty(String dateStr) {
		return (dateStr == null || dateStr.trim().length() == 0);
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		boolean result = false;
		if (!isEmpty(dateStr)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				sdf.parse(dateStr);
				result = true;
			} catch (ParseException e) {}
		}
		return result;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static boolean isValidDecimal(String value) {
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
		boolean result = false;
		if (!isEmpty(value)) {
			
			try {
				df.parse(value);
				result = true;
			} catch (ParseException e) {}
		}
		return result;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static Number formatNumber(String value) {
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
		if (isValidDecimal(value)) {
			try {
				return df.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static String formatNumber(Number value) {
		DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
		return df.format(value);
	}
	
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date formatDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (isValidDate(dateStr, pattern)) {
			try {
				return sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
