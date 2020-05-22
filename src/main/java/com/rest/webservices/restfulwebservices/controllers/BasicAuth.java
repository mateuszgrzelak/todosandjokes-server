package com.rest.webservices.restfulwebservices.controllers;

import com.rest.webservices.restfulwebservices.pojo.Response;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasicAuth {

    @GetMapping(path="/basicauth")
    public Response helloWorldBean(){
        return new Response("Wszystko dziala!");
    }
}
