package com.proto.demo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayEventFunction implements Function<Message<Map<String, Object>>, Message<Map<String, Object>>> {

    @Autowired
    private HistoryRepository hisRepository;

    @Override
    public Message<Map<String, Object>> apply(Message<Map<String, Object>> input) {
        String main = (String) input.getPayload().get("body");
        String id = main.split(",")[0];
        id = id.split(":")[1];
        String point = main.split(",")[1];
        point = point.replace("}", "").split(":")[1];
        System.out.println(point);
        int customerId = Integer.parseInt(id);
        int pointValue = Integer.parseInt(point);

        History hist = new History();
        hist.setCustomer_id(customerId);
        hist.setPoint(pointValue);
        hist.setType("SpringNative");
        hist.setProperties("SpringDataJDBC");

        Map<String, Object> authMap = (Map<String, Object>) ((Map<String, Object>) input.getPayload().get("requestContext")).get("authorizer");
        Map<String, Object> userMap = (Map<String, Object>) authMap.get("claims");
        String username = (String)userMap.get("cognito:username");
        hist.setUpdated_by_user(username);

        hist.setUpdated_datetime(new Timestamp(System.currentTimeMillis()));

        History save = hisRepository.save(hist);
        System.out.print(save.getId());

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("statusCode", 201);
        returnMap.put("body", "data inserted successfully!");
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;
    }
 }