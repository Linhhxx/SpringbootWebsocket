package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: lin_charles
 * @create: 2020-04-29 17:20:01
 * @program: SpringbootWebsocket
 * @description:
 */
@Controller
@RequestMapping("/socketTest")
public class SocketTestController {

    @RequestMapping("/socket")
    public String socket(){
        return "test";
    }
}
