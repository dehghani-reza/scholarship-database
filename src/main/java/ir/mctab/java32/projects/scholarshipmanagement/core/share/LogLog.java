package ir.mctab.java32.projects.scholarshipmanagement.core.share;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LogLog {
    private static LogLog logLog;

    public static LogLog getInstance() {
        if (logLog == null) {
            logLog = new LogLog();
        }
        return logLog;
    }

    private LogLog() {
    }


    public Integer getResultLogLog(String action) {
        Connection connection = null;
        String sqlLog = "INSERT INTO `scholarship`.`scholarship_log` " +
                "(`action`, `date`, `userid`,`scholarshipid`) " +
                "VALUES ( ?, ?, ?, ?)";
        try {
            connection = DatabaseConfig.getDatabaseConnection();
            PreparedStatement preparedStatementLog = connection.prepareStatement(sqlLog);
            preparedStatementLog.setString(1, action);
            preparedStatementLog.setString(2, AuthenticationService.getInstance().getDate());
            preparedStatementLog.setLong(3, AuthenticationService.getInstance().getLoginUser().getId());
            preparedStatementLog.setInt(4,0);
            return  preparedStatementLog.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
