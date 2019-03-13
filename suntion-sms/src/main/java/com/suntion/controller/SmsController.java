package com.suntion.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.suntion.service.AuthFeginClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sms")
public class SmsController {

    @Value("${server.port}")
    private Integer port;

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping("/{content}/{phone}")
    public String sms(@PathVariable String phone, @PathVariable String content) {
        if(phone.startsWith("e")) {
            throw  new RuntimeException("sms错误");
        }

        if (content.equals("超时")) {
            try {
                Thread.sleep(4500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return phone+"-" + content + "-----"+port;
    }

    public String error(@PathVariable String phone, @PathVariable String content) {
        //发起某个网络请求（可能失败）
        return "sms中发生熔断";
    }

    @GetMapping("/test")
    public String sms() {
        return "-----test" + port;
    }

    @Autowired
    AuthFeginClient authFeginClient;

    @GetMapping("/fegin/auth/jobs")
    public Object jobs() {
        return authFeginClient.getJobs();
    }
}
