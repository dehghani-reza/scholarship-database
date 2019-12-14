package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.ScholarshipLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.RequestScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestScholarshipByStudentUseCaseImpl implements RequestScholarshipByStudentUseCase {
    @Override
    public int start(List<String> information) {
        User loginUser = AuthenticationService.getInstance().getLoginUser();

        if (loginUser != null) {
            if (loginUser.getRole().equals("Student")) {
                // connection
                Connection connection = null;
                try {
                    connection = DatabaseConfig.getDatabaseConnection();
                    // query
                    String sql = "INSERT INTO scholarship.scholarship" +
                            " (status, name, family, nationalCode, lastUni, lastDegree, lastField, lastScore, applyUni, applyDegree, applyField, applyDate, username) " +
                            " VALUES" +
                            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?)";
                    // result
                    PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1,"RequestedByStudent");
                    preparedStatement.setString(2,information.get(0));
                    preparedStatement.setString(3,information.get(1));
                    preparedStatement.setString(4,information.get(2));
                    preparedStatement.setString(5,information.get(3));
                    preparedStatement.setString(6,information.get(4));
                    preparedStatement.setString(7,information.get(5));
                    preparedStatement.setFloat(8, Long.parseLong(information.get(6)));
                    preparedStatement.setString(9,information.get(7));
                    preparedStatement.setString(10,information.get(8));
                    preparedStatement.setString(11,information.get(9));
                    preparedStatement.setString(12,information.get(10));
                    preparedStatement.setString(13,AuthenticationService.getInstance().getLoginUser().getUsername());
                    int status =preparedStatement.executeUpdate();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    //log
                    while (resultSet.next()){
                        if(status!=0){
                            int i1= ScholarshipLog.getInstance().getResultScholarshipLog("RequestScholarshipByStudent",resultSet.getInt(1));
                            if (i1!=0){
                                System.out.println(status+" row('s) affected");
                            }
                        }else {
                            System.out.println("you can't change this scholarship status");
                        }
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }


        return 0;
    }
}