
package com.ctm.eadvogado.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Calendar>
{
	public static String DATETIME_PATTERN_IN = "yyyyMMddHHmmss";
	public static String DATETIME_PATTERN_OUT = "yyyyMMddHHmmssZ";
	public static SimpleDateFormat sdf_in = new SimpleDateFormat(DATETIME_PATTERN_IN);
	public static SimpleDateFormat sdf_out = new SimpleDateFormat(DATETIME_PATTERN_OUT);

    public Calendar unmarshal(String value) {
        return TipoDataHoraConverter.unmarshal(value);
    }

    public String marshal(Calendar value) {
        return TipoDataHoraConverter.marshal(value);
    }

}

