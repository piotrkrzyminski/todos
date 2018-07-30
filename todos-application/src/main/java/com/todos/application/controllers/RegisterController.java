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

        LOG.debug("Returning registration page");

        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handleRegister(@ModelAttribute("registerData") @Validated RegisterData registerData,
                                 BindingResult result, Model model) {

        if (result.hasErrors()) {
            LOG.debug("Register data has some errors. Registration cannot be performed");
            model.addAttribute("error", true);
        } else {

            try {
                userFacade.register(registerData);
                LOG.debug("User with login " + registerData.getLogin() + " registered successfully");
                return "home";
            } catch (Exception e) {
                LOG.debug("User cannot be registered");
                model.addAttribute("error", true);
                LOG.debug(e.getMessage());
            }
        }

        return "register";
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
