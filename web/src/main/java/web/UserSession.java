package web;

import model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private User user;
    private boolean loginFailed;
    private boolean usernameTaken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(boolean loginFailed) {
        this.loginFailed = loginFailed;
    }

    public boolean isUsernameTaken() {
        return usernameTaken;
    }

    public void setUsernameTaken(boolean usernameTaken) {
        this.usernameTaken = usernameTaken;
    }

    // Method for checking URL access rights
    public Boolean urlAccessRights(String userType) throws Exception {
        if (user != null){
            String userRole = user.getRole();
            if (userRole.equals(userType) ){
                return true;
            }
        }
        return false;
    }
}
