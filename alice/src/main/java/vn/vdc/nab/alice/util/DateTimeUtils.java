package vn.vdc.nab.alice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils {

	public static final String DEFAULT_FULL_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static Date getUTCTodayWithPattern(String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		return formatter.parse(formatter.format(new Date()));
	}

}
