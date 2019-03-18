package com.netty.server.netty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by xsw on 2017/10/12.
 */
@Controller
public class UrlController {

    @RequestMapping("/")
    public String webSocketChatClient(){
        return "/WebSocketChatClient";
    }

}
