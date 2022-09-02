package com.hypnotoid.MySite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
public class ErrorController {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping("/403")
    public String error403(){
        return "error";
    }

   @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String errorPageget() {
        return "error";
    }

    @RequestMapping(value = "/error",method = RequestMethod.POST)
    public String errorPage() {
        return "error";
    }
}
