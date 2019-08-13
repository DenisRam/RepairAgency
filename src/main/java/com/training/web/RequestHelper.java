package com.training.web;

import com.training.web.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class RequestHelper {

    private static RequestHelper instance = null;
    private HashMap<String, Command> commands = new HashMap<>();

    private RequestHelper(){
        commands.put("setLocale", new ChangeLocaleCommand());
        commands.put("createUser", new CreateUserCommand());
        commands.put("back", new BackCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("singIn", new SingInCommand());
        commands.put("cancelCreareUser", new CancelCreateUserCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("nullCommand", new NullCommand());
        commands.put("AddComment", new AddCommentCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("refuseOrder", new RefuseOrderCommand());
        commands.put("confirmOrder", new ConfirmOrderCommand());
        commands.put("startRepair", new StartRepairCommand());
        commands.put("finishRepair", new FinishRepairCommand());

    }


    public Command getCommand(HttpServletRequest request) {
        String actionName = request.getParameter("command");
        if (actionName == null) {
            actionName = "nullCommand";
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
