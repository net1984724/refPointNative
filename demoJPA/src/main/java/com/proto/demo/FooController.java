package com.proto.demo;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import reactor.core.publisher.Mono;

@Controller
public class FooController {
    
    @Autowired
    MainService srv;

    public Message<Map<String, Object>> apply(Message<Map<String, Object>> input) {

        Map<String, Object> authMap = (Map<String, Object>) ((Map<String, Object>) input.getPayload().get("requestContext")).get("authorizer");
        Map<String, Object> userMap = (Map<String, Object>) authMap.get("claims");
        String username = (String)userMap.get("cognito:username");
        srv.addPoint(username);

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("statusCode", 201);
        returnMap.put("body", "data inserted successfully!");
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;
    }
}