package uk.ac.ncl.team15.android.util;

import android.telephony.PhoneNumberUtils;

import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class UserAttributeValidators {
    private static final String COUNTRY_ISO = "UK";

    public interface Validator {
        boolean valid(ModelUser mu, String val);
    }

    public static final Validator NOT_EMPTY = ((mu, val) -> val != null && !val.trim().isEmpty());

    // very general as phone num formats differ massively
    public static final Validator PHONE_NUM = matchesRegex("\\+?[0-9]+");

    public static final Validator DOB =
            matchesRegex("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");

    public static final Validator EMAIL =
            matchesRegex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public static Validator matchesRegex(String regex) {
        return (((mu, val) -> val.matches(regex)));
    }
}
