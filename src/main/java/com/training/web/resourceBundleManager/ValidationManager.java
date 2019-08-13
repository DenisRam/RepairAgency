package com.training.web.resourceBundleManager;

import java.util.ResourceBundle;

public class ValidationManager {


    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("validation");

    private ValidationManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
