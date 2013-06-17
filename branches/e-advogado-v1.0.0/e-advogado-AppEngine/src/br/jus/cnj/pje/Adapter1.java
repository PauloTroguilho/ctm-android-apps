
package br.jus.cnj.pje;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Calendar>
{
	public static String DATETIME_PATTERN = "yyyyMMddHHmmss";
	public static SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_PATTERN);

    public Calendar unmarshal(String value) {
    	Date date = null;
    	try {
    		date = sdf.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
        return calendar;
    }

    public String marshal(Calendar value) {
        if (value == null) {
            return null;
        }
        
        return sdf.format(value.getTime());
    }

}
