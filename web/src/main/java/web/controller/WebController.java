package web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import web.UserSession;

public class WebController {

    @Autowired
    private UserSession userSession;

    //Checks user role for controllers to access
    public Boolean urlAccessRights(String userType) {
        if (userSession.getUser() != null){
            String userRole = userSession.getUser().getRole();
            if (userRole.equals(userType) ) {
                return true;
            }
        }
        return false;
    }
}
