package com.wanted.nution.common;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@Getter
public class DBManager {

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_NAME;
    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER_ID;
    @Value("${spring.datasource.password}")
    private String USER_PW;

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER_ID, USER_PW);
    }

    public void releaseCon(Connection con, Statement statement) {
        try{
            if (statement != null){
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void releaseCon(Connection con, Statement statement, ResultSet resultSet) {
        try{
            if (resultSet != null)resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        releaseCon(con, statement);
    }

}
