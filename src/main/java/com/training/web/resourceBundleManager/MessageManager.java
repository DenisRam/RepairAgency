package com.training.web.resourceBundleManager;

import java.util.ResourceBundle;

public class MessageManager {

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

    private MessageManager(){}

    public static String getProperty(String key){
        return resourceBundle.getString(key);
    }
}
