package com.todos.application.controllers;

import com.todos.application.facades.UserFacade;
import com.todos.data.RegisterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "register")
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    private UserFacade userFacade;

    /**
     * Show registration page and add register data object to model.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("registerData", new RegisterData());

        return "register";
    }

    /**
     * Process register form data and create account if data is proper.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String handleRegister(@ModelAttribute("registerData") @Validated RegisterData registerData,
                                 BindingResult result, Model model) {

        if (!result.hasErrors()) {

            try {
                userFacade.register(registerData);
                return "home";
            } catch (Exception e) {
                LOG.warn(e.getMessage());
            }
        }

        model.addAttribute("error", true);
        return "register";
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
