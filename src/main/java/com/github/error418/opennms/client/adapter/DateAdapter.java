package com.github.error418.opennms.client.adapter;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	public static final ThreadLocal<DateFormat> FORMATTER_LONG_GMT = new ThreadLocal<DateFormat>() {
        @Override
        protected synchronized DateFormat initialValue() {
            final DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG, Locale.ENGLISH);
            formatter.setLenient(true);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            return formatter;
        }
    };
	
	@Override
	public String marshal(Date date) throws Exception {
		return FORMATTER_LONG_GMT.get().format(date);
	}

	@Override
	public Date unmarshal(String string) throws Exception {
		// Only one way is needed in this case
		return null;
	}
}
