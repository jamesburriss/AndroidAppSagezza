package uk.ac.ncl.team15.android;

/**
 * @Purpose: The user validator  Activity validates the authenticity of an user.
 *
 * @authors  Callum Errington
 * @since   1/5/2018
 * extended by @authors
 *
 **/

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import uk.ac.ncl.team15.android.retrofit.models.ModelUser;
import uk.ac.ncl.team15.android.util.UserAttributeValidators;

public class UserAttribute {
    private String key;
    private String value;
    private UserAttribSetter setter;
    private UserAttributeValidators.Validator validator = null;
    private Consumer<ModelUser> actionCallback = null;
    private Object actionIcon;
    private Map<String, String> options = null;

    UserAttribute(String key, String value, UserAttribSetter setter) {
        this.key = key;
        this.value = value;
        this.setter = setter;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    void setValue(ModelUser mu, String val) {
        setter.set(mu, val);
    }

    public UserAttributeValidators.Validator getValidator() {
        return validator;
    }

    boolean isValid(ModelUser mu, String val) {
        if (validator == null)
            return true;
        return validator.valid(mu, val);
    }

    public UserAttribute setValidator(UserAttributeValidators.Validator validator) {
        this.validator = validator;
        return this;
    }

    public UserAttribute setAction(Object actionIcon, Consumer<ModelUser> actionCallback) {
        this.actionIcon = actionIcon;
        this.actionCallback = actionCallback;
        return this;
    }

    public UserAttribute setOptions(Map<String, String> options) {
        this.options = options;
        return this;
    }

    public Object getActionIcon() {
        return actionIcon;
    }

    public Consumer<ModelUser> getActionCallback() {
        return actionCallback;
    }

    void onAction(ModelUser mu) {
        actionCallback.accept(mu);
    }

    public Map<String, String> getOptions() {
        if (this.options == null)
            return null;
        return Collections.unmodifiableMap(this.options);
    }

    interface UserAttribSetter {
        public void set(ModelUser mu, String val);
    }
}
