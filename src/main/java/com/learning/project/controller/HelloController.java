package com.learning.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Youngz
 * @date 2019/7/30 - 17:16
 */
@Controller
public class HelloController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
