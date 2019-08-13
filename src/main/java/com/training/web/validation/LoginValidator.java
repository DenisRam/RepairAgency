package com.training.web.validation;

import com.training.web.resourceBundleManager.ValidationManager;

import java.util.HashMap;
import java.util.Map;

public class LoginValidator {

    public static Map<String, Boolean> validate(String login, String password){
        Map<String, Boolean> map = new HashMap<>();

        if(login == null || !login.matches(ValidationManager.getProperty("user.login"))){
            map.put("incorectLogin", true);
        }

        if(password == null || !password.matches(ValidationManager.getProperty("user.password"))){
            map.put("incorectPassword", true);
        }
        return map;
    }
}
