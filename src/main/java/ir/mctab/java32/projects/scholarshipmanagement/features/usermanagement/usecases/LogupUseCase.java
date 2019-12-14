package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases;

import ir.mctab.java32.projects.scholarshipmanagement.model.User;

public interface LogupUseCase {
    User logup(String username, String password , String date);
}
