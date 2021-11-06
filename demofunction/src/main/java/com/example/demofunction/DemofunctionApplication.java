package com.example.demofunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemofunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemofunctionApplication.class, args);
	}

	@Bean
    public Function<Mono<Map<String, Object>>, String> handler() {
        return request -> handleRequest(request);
    }

    private static final String CONNECTION_STRING = "jdbc:mysql://pointsystem.cluster-c31yijaabbwk.us-east-2.rds.amazonaws.com:3306/pointsystem?enabledTLSProtocols=TLSv1.2";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";
  //   private static final int MAX_RETRIES = 5;
  //   private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
//      private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
          
    public String handleRequest(Mono<Map<String, Object>> input) {
          Connection conn = null;
          Statement stmt = null;
          try {
              conn = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
              stmt = conn.createStatement();
              String sql = "INSERT INTO history (customer_id, point, type, properties, updated_by_user, updated_datetime) VALUES (3110, 100, 'Java(SpringNative)', 'JDBC Test', 'testUser', current_timestamp)";
              stmt.executeUpdate(sql);
          } catch (SQLException se) {
              //Handle errors for JDBC
              se.printStackTrace();
          } catch (Exception e) {
              //Handle errors for Class.forName
              e.printStackTrace();
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
              }//end finally try
          }//end try
          return "complete";
      }
}
