package com.proto.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.Mono;

@Controller
public class FooController {
    
    @Autowired
    MainService srv;

    public String apply(Mono<Map<String, Object>> input) {
        //MainService srv = new MainService();
        srv.addPoint();
        return "apply end.";
    }
    
}