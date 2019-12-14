package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.ScholarshipLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.RejectScholarshipBySupervisorUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RejectScholarshipBySupervisorUseCaseImpl implements RejectScholarshipBySupervisorUseCase {
    @Override
    public int reject(Long scholarshipId) {
        User user = AuthenticationService.getInstance().getLoginUser();
        int done = 0;
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
                String sql = "update scholarship set status = 'RejectedBySupervisor' " +
                        "where id = ?";
                // execute
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                done = preparedStatement.executeUpdate();
                //log
                if(done!=0){
                    int i1= ScholarshipLog.getInstance().getResultScholarshipLog("RejectedBySupervisor",scholarshipId);
                    if (i1!=0){
                        System.out.println(done+" row('s) affected");
                    }
                }else {
                    System.out.println("you can't change this scholarship status");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return done;
    }
}
