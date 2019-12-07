package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;

public class   LogoutUseCase implements ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase {
    public  void logout() {
        // get connection
        AuthenticationService.getInstance().setLoginUser(null);
    }
}

