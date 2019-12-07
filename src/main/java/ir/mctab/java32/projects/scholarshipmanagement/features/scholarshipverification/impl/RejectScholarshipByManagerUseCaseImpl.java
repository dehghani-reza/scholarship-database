package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.RejectScholarshipByManagerUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class RejectScholarshipByManagerUseCaseImpl implements RejectScholarshipByManagerUseCase {
    @Override
    public void rejectByManager(Long scholarshipId) {
        User user = AuthenticationService.getInstance().getLoginUser();

        if (user != null && user.getRole().equals("Manager")) {

            // connection
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                // sql
                String sql = "update scholarship set status = 'RejectedByManager' " +
                        "where id = ?";
                // execute
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                preparedStatement.executeUpdate();
                //log
                String sqlLog = "INSERT INTO `scholarship`.`scholarship_log` " +
                        "(`action`, `date`, `userid`, `scholarshipid`) " +
                        "VALUES ( ?, ?, ?, ?)";
                PreparedStatement preparedStatementLog = connection.prepareStatement(sqlLog);
                preparedStatementLog.setString(1,"RejectedByManager");
                preparedStatementLog.setString(2,AuthenticationService.getInstance().getDate());
                preparedStatementLog.setLong(3,user.getId());
                preparedStatementLog.setLong(4,scholarshipId);
                preparedStatementLog.executeUpdate();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
