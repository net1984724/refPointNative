package com.proto.demo;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;

import com.amazonaws.services.rdsdata.AWSRDSData;
import com.amazonaws.services.rdsdata.AWSRDSDataClient;
import com.amazonaws.services.rdsdata.model.ExecuteStatementRequest;
import com.amazonaws.services.rdsdata.model.ExecuteStatementResult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    public Function<Message<Map<String, Object>>, Message<Map<String, Object>>> handler() {
        return request -> handleRequest(request);
    }

	public static final String RESOURCE_ARN = "arn:aws:rds:us-east-2:079862682662:cluster:pointsystem";
    public static final String SECRET_ARN = "arn:aws:secretsmanager:us-east-2:079862682662:secret:point-mysql-B3EQyV";

    public Message<Map<String, Object>> handleRequest(Message<Map<String, Object>> input) {

        String main = (String) input.getPayload().get("body");
        String id = main.split(",")[0];
        id = id.split(":")[1];
        String point = main.split(",")[1];
        point = point.replace("}", "").split(":")[1];
        int customerId = Integer.parseInt(id);
        int pointValue = Integer.parseInt(point);

		Map<String, Object> authMap = (Map<String, Object>) ((Map<String, Object>) input.getPayload().get("requestContext")).get("authorizer");
		Map<String, Object> userMap = (Map<String, Object>) authMap.get("claims");
		String username = (String)userMap.get("cognito:username");

		String sql = "INSERT INTO history (customer_id, point, type, properties, updated_by_user, updated_datetime) VALUES (%1$d, %2$d, 'Java', 'javaTest', '%3$s', current_timestamp)";
        sql = String.format(sql, customerId, pointValue, username);
        System.out.println(sql);

        AWSRDSData rdsData = AWSRDSDataClient.builder().build();
        ExecuteStatementRequest rdsRequest = new ExecuteStatementRequest()
                .withResourceArn(RESOURCE_ARN)
                .withSecretArn(SECRET_ARN)
                .withDatabase("pointsystem")
                .withSql(sql);
        rdsData.executeStatement(rdsRequest);
//        RdsDataClient client = RdsDataClient.builder().build();

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("statusCode", 201);
        returnMap.put("body", "data inserted successfully!");
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;

	}
}
