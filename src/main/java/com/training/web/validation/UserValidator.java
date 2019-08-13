package com.training.web.validation;

import com.training.web.resourceBundleManager.ValidationManager;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {

    public static Map<String, Boolean> validate(String name, String surname){
        Map<String, Boolean> map = new HashMap<>();

        if(name == null || !name.matches(ValidationManager.getProperty("user.name"))){
            map.put("incorrectName", true);
        }

        if(surname == null || !surname.matches(ValidationManager.getProperty("user.surname"))){
            map.put("incorrectSurName", true);
        }
        return map;
    }
}
