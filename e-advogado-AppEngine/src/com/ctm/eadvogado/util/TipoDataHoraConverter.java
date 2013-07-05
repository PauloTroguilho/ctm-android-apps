package com.ctm.eadvogado.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class TipoDataHoraConverter{
	
	public static String DATETIME_PATTERN = "yyyyMMddHHmmss";
	
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			DATETIME_PATTERN);

	public static Calendar unmarshal(String value) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static String marshal(Calendar value) {
		if (value == null) {
			return null;
		}
		return simpleDateFormat.format(value.getTime());
	}

}
