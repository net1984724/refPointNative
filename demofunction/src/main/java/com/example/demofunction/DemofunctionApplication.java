package com.example.demofunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class DemofunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemofunctionApplication.class, args);
	}

	@Bean
    public Function<Message<Map<String, Object>>, Message<Map<String, Object>>> handler() {
        return request -> handleRequest(request);
    }

    private static final String CONNECTION_STRING = "jdbc:mysql://pointsystem.cluster-c31yijaabbwk.us-east-2.rds.amazonaws.com:3306/pointsystem?enabledTLSProtocols=TLSv1.2";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";
  //   private static final int MAX_RETRIES = 5;
  //   private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
//      private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
          
    public Message<Map<String, Object>> handleRequest(Message<Map<String, Object>> input) {
        Connection conn = null;
        Statement stmt = null;
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            Map<String, Object> authMap = (Map<String, Object>) ((Map<String, Object>) input.getPayload().get("requestContext")).get("authorizer");
            Map<String, Object> userMap = (Map<String, Object>) authMap.get("claims");
            String username = (String)userMap.get("cognito:username");
            String sql = "INSERT INTO history (customer_id, point, type, properties, updated_by_user, updated_datetime) VALUES (3110, 100, 'Java(SpringNative)', 'JDBC Test', "+ "'" + username + "', current_timestamp)";
//            sql = String.format(username);
            stmt.executeUpdate(sql);

            returnMap.put("statusCode", 201);
            returnMap.put("body", "data inserted successfully!");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            returnMap.put("statusCode", 500);
            returnMap.put("body", "SQLException!");
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            returnMap.put("statusCode", 500);
            returnMap.put("body", "Exception!");
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
                returnMap.put("statusCode", 500);
                returnMap.put("body", "finally SQLException!");
            }//end finally try
        }//end try
//        return "complete";
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;
    }
}
