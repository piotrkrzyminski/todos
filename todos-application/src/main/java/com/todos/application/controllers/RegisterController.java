package com.todos.application.controllers;

import com.todos.application.facades.UserFacade;
import com.todos.core.services.exceptions.DuplicateUserException;
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

    private final UserFacade userFacade;

    @Autowired
    public RegisterController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Show registration page and add register data object to model.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("registerData", new RegisterData());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleRegister(@ModelAttribute("registerData") @Validated RegisterData registerData,
                                 BindingResult result, Model model) {

        if(result.hasErrors()) {
            model.addAttribute("error", true);
        } else {

            try {
                userFacade.register(registerData);
                return "home";
            } catch (DuplicateUserException e) {
                model.addAttribute("duplicated", true);
                LOG.debug(e.getMessage());
            }
        }

        return "register";
    }
}
