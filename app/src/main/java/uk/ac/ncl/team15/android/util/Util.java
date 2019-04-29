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

    /**
     * Directly copied from: https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
     *
     * Method converts number of bytes to a human readable string with units
     */
    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
