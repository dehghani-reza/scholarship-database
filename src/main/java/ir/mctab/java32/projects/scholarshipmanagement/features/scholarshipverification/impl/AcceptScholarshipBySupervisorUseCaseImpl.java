package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.AcceptScholarshipBySupervisorUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AcceptScholarshipBySupervisorUseCaseImpl implements AcceptScholarshipBySupervisorUseCase {
    @Override
    public int accept(Long scholarshipId) {
        int done = 0;
        User user = AuthenticationService.getInstance().getLoginUser();
        //checking
        Connection connection = null;
        boolean status = false;
        try {
            connection = DatabaseConfig.getDatabaseConnection();
            String sqlCheck = "select id from scholarship where status = 'RequestedByStudent' and id=" + scholarshipId;
            PreparedStatement preparedStatementCheck = connection.prepareStatement(sqlCheck);
            ResultSet resultSetCheck = preparedStatementCheck.executeQuery();
            while (resultSetCheck.next()) {
                if(!resultSetCheck.wasNull()){
                    System.out.println(resultSetCheck.getLong("id ")+"changing...");
                    status = true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user != null && user.getRole().equals("Supervisor")&&status) {

            // connection
            try {
                // Connection connection = DatabaseConfig.getDatabaseConnection();

                // sql
                String sql = "update scholarship set status = 'AcceptedBySupervisor' " +
                        "where id = ?";

                // execute
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                done  =preparedStatement.executeUpdate();
                //log
                String sqlLog = "INSERT INTO `scholarship`.`scholarship_log` " +
                        "(`action`, `date`, `userid`, `scholarshipid`) " +
                        "VALUES ( ?, ?, ?, ?)";
                PreparedStatement preparedStatementLog = connection.prepareStatement(sqlLog);
                preparedStatementLog.setString(1, "AcceptedBySupervisor");
                preparedStatementLog.setString(2, AuthenticationService.getInstance().getDate());
                preparedStatementLog.setLong(3, user.getId());
                preparedStatementLog.setLong(4, scholarshipId);
                preparedStatementLog.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return done;
    }
}

