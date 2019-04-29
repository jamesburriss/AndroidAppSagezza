package uk.ac.ncl.team15.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Misc utility class
 */
public class Util {
    public static String convertISO8601To(String iso8601, String timezone, String fmt) throws ParseException {
        DateFormat dfFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        dfFrom.setTimeZone(TimeZone.getTimeZone(timezone));
        Date dateTime = dfFrom.parse(iso8601);

        DateFormat dfTo = new SimpleDateFormat(fmt);
        return dfTo.format(dateTime);
    }
}
