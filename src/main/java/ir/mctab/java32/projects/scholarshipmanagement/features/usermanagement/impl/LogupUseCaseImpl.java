package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.LogLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogupUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogupUseCaseImpl implements LogupUseCase {
    @Override
    public User logup(String username, String password, String date) {

        // get connection
        try {
            Connection connection = DatabaseConfig.getDatabaseConnection();
            String sql = "INSERT INTO `scholarship`.`user` (`username`, `password`, `role`)" +
                    " VALUES ('"+username+"','"+password+"', 'Student') ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long id=0;
            while (resultSet.next()) {
                        id = resultSet.getLong(1);
            }
            User user1 = new User(id,username,password,"Student");
            AuthenticationService.getInstance().setLoginUser(user1);
            AuthenticationService.getInstance().setDate(date);
            if(AuthenticationService.getInstance().getLoginUser()!=null){
                int i1= LogLog.getInstance().getResultLogLog("Logup");
            }
            return user1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return result
        return null;

    }
}
