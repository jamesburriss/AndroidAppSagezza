package uk.ac.ncl.team15.android.util;

import java.util.regex.Pattern;

import uk.ac.ncl.team15.android.UserProfileActivity;
import uk.ac.ncl.team15.android.retrofit.models.ModelUser;

public class UserAttributeValidators {
    public interface Validator {
        boolean valid(ModelUser mu, String val);
    }

    public static final Validator NOT_EMPTY = ((mu, val) -> val != null && !val.isEmpty());

    public static Validator matchesRegex(String regex) {
        return (((mu, val) -> val.matches(regex)));
    }
}