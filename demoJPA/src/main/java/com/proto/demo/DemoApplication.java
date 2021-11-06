package com.proto.demo;

import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
public class DemoApplication {

	@Autowired
	FooController foo;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
    public Function<Mono<Map<String, Object>>, String> handler() {
		return request -> foo.apply(request);
    }

//	@Bean
//	public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> uppercase() {
//		Foobar foo = new Foobar();
//		return value -> foo.regist(value);
//	}
}