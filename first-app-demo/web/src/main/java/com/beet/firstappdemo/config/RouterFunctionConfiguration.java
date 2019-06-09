package com.beet.firstappdemo.config;

import java.util.Collection;

import com.beet.firstappdemo.domain.User;
import com.beet.firstappdemo.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;

/**
 * RouterFunctionConfiguration
 */
@Configuration
public class RouterFunctionConfiguration {

    @Bean

    public RouterFunction<ServerResponse> personFindAll(UserRepository userRepository){

        Collection<User> users = userRepository.findAll();

        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),

            request -> {

                Flux<User> userFlux = Flux.fromIterable(users);

                return ServerResponse.ok().body(userFlux,User.class);

            });

    }
}