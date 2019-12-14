package ir.mctab.java32.projects.scholarshipmanagement.core.share;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScholarshipLog {


    private static ScholarshipLog scholarshipLog;

    public static ScholarshipLog getInstance() {
        if (scholarshipLog == null) {
            scholarshipLog = new ScholarshipLog();
        }
        return scholarshipLog;
    }

    private ScholarshipLog() {
    }


    public Integer getResultScholarshipLog(String action, long scholarshipId) {
        Connection connection = null;
        String sqlLog = "INSERT INTO `scholarship`.`scholarship_log` " +
                "(`action`, `date`, `userid`, `scholarshipid`) " +
                "VALUES ( ?, ?, ?, ?)";
        try {
            connection = DatabaseConfig.getDatabaseConnection();
            PreparedStatement preparedStatementLog = connection.prepareStatement(sqlLog);
            preparedStatementLog.setString(1, action);
            preparedStatementLog.setString(2, AuthenticationService.getInstance().getDate());
            preparedStatementLog.setLong(3, AuthenticationService.getInstance().getLoginUser().getId());
            preparedStatementLog.setLong(4, scholarshipId);
            return  preparedStatementLog.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
//    public String mamad(String string){
//        return switch (string){
//            case "mamad" -> "m";
//            case "ali" -> "a";
//            default -> throw new IllegalStateException("Unexpected value: " + string);
//        };
//    }
}
