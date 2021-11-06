package com.example.demofunction;

import java.util.Map;
import java.util.function.Function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.springframework.stereotype.Component;

@Component
public class ApiGatewayEventFunction implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        String name = null;

        Map<String, String> queryStringParameter = input.getQueryStringParameters();
        if( queryStringParameter == null ) {
            name = "\"NULL\"";
        } else {
            String id = queryStringParameter.get("id");
            name = "\"Not null:\"" + id;
        }

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setStatusCode(200);
        responseEvent.setBody("{\"name\":" + name + "}");
        return responseEvent;
    }
 }