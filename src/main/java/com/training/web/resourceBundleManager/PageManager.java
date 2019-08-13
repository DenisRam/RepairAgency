package com.training.web.resourceBundleManager;

import java.util.ResourceBundle;

public class PageManager {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("pages");

    private PageManager() {

    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
