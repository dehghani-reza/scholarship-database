package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.DashboardScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardScholarshipByStudentUseCaseImpl implements DashboardScholarshipByStudentUseCase {
    @Override
    public Map<String, Integer> listScholarshipsStatus() {
        User loginUser = AuthenticationService.getInstance().getLoginUser();
        Map<String,Integer> status = new HashMap<String, Integer>();

        if (loginUser != null) {
            if (loginUser.getRole().equals("Student")) {
                // connection
                Connection connection ;
                try {
                    connection = DatabaseConfig.getDatabaseConnection();
                    // query
                    String sql = "SELECT status, count(*) as NUM FROM scholarship where username= '"+loginUser.getUsername()+"'"+"GROUP BY status" ;
                    // result
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        status.put(resultSet.getString("status"),resultSet.getInt("NUM"));

                    }
                    return status;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}
