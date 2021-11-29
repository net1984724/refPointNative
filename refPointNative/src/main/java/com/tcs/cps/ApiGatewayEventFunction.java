package com.tcs.cps;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tcs.cps.repository.HistoryRepository;

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
        Map<String, Object> authMap = (Map<String, Object>) ((Map<String, Object>) input.getPayload().get("requestContext")).get("authorizer");
        Map<String, Object> userMap = (Map<String, Object>) authMap.get("claims");
        String username = (String)userMap.get("cognito:username");
         
        String sumPoints = hisRepository.getSumPointsByUsername(username);

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("statusCode", 201);
        returnMap.put("body", sumPoints);
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;

      }
 }