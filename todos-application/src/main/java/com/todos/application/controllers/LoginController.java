package com.todos.application.controllers;

import com.todos.data.LoginData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "login")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String getLogin(Model model) {
        model.addAttribute("loginData", new LoginData());

        return "login";
    }
}
