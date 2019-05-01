package uk.ac.ncl.team15.android.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Purpose: Misc utility class
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/
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

    /* Directly copied from: https://stackoverflow.com/questions/5568874/how-to-extract-the-file-name-from-uri-returned-from-intent-action-get-content/25005243#25005243
     *
     */
    public static String getFileName(Uri uri, ContentResolver resolver) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = resolver.query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
