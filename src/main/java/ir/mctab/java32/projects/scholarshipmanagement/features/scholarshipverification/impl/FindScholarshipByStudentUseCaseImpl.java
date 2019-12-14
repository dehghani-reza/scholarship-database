package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.FindScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindScholarshipByStudentUseCaseImpl implements FindScholarshipByStudentUseCase {
    @Override
    public List<Scholarship> listScholarships() {
        User loginUser = AuthenticationService.getInstance().getLoginUser();
        List<Scholarship> result = new ArrayList<Scholarship>();
        if (loginUser != null) {
            if (loginUser.getRole().equals("Student")) {
                // connection
                Connection connection = null;
                try {
                    connection = DatabaseConfig.getDatabaseConnection();
                    // query
                    String sql = "select * from scholarship where username = "+"'"+loginUser.getUsername()+"'";
                    // result
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Scholarship scholarship = new Scholarship(
                                resultSet.getLong("id"),
                                resultSet.getString("status"),
                                resultSet.getString("name"),
                                resultSet.getString("family"),
                                resultSet.getString("nationalCode"),
                                resultSet.getString("lastUni"),
                                resultSet.getString("lastDegree"),
                                resultSet.getString("lastField"),
                                resultSet.getFloat("lastScore"),
                                resultSet.getString("applyUni"),
                                resultSet.getString("applyDegree"),
                                resultSet.getString("applyField"),
                                resultSet.getString("applyDate")
                        );
                        result.add(scholarship);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
