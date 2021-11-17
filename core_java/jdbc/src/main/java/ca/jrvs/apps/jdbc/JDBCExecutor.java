package ca.jrvs.apps.jdbc;

import org.apache.log4j.BasicConfigurator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

    private final static Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

    public static void main(String[] args) {
        //Use default logger config
        BasicConfigurator.configure();

        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport", "postgres", "password");
        try{
            Connection connection = dcm.getConnection();
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);
        }catch(SQLException e){
            JDBCExecutor.logger.error("Error: Unable to process", e);
        }
    }
}
