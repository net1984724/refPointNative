package com.tcs.cps;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tcs.cps.model.History;
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
        String main = (String) input.getPayload().get("body");

//        History save = hisRepository.save(hist);

        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("statusCode", 201);
        returnMap.put("body", "data inserted successfully!");
        Message<Map<String, Object>> message = MessageBuilder.withPayload((Map<String, Object>)returnMap).setHeader("Access-Control-Allow-Origin", "*").build();
        return message;
    }
 }