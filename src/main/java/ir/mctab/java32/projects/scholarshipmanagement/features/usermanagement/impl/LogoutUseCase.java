package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.LogLog;

public class   LogoutUseCase implements ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase {
    public  void logout() {
        // get connection
        if(AuthenticationService.getInstance().getLoginUser()!=null){
            int i1= LogLog.getInstance().getResultLogLog("Logout");
        }
        AuthenticationService.getInstance().setLoginUser(null);
    }
}

