package com.training.controller;

import com.training.controller.commands.Command;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class RequestHelper {

    private static RequestHelper instance = null;
    private HashMap<String, Command> commands = new HashMap<>();

    private RequestHelper(){

    }


    public Command getCommand(HttpServletRequest request) {
        String actionName = request.getParameter("command");
        if (actionName == null) {
            actionName = "noCommand";
        }
        return commands.get(actionName);
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
