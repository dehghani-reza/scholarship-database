package ir.mctab.java32.projects.scholarshipmanagement.core.share;

import ir.mctab.java32.projects.scholarshipmanagement.model.User;

public class AuthenticationService {

    private User loginUser;
    private String date;

    private static AuthenticationService authenticationService = null;
    public static AuthenticationService getInstance() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService();
        }
        return authenticationService;
    }

    private AuthenticationService() {

    }

    public void setLoginUser(User user) {
        this.loginUser = user;
    }

    public User getLoginUser() {
        return loginUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
