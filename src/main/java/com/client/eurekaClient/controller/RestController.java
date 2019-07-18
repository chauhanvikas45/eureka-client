package com.client.eurekaClient.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired(required = true )
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/hello")
    @HystrixCommand(fallbackMethod = "defaultResponse")
    public String hello(){
        Application application = eurekaClient.getApplication("vikas2");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/"+"hello1";
        //String url = "http://localhost:8099/hello1";
        System.out.println(url);
        String response = restTemplate.getForObject(url,String.class);
        System.out.println(response);
        return response;
    }

    public String defaultResponse(){
     return "failing instanse";
    }


/*
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }*/

}
