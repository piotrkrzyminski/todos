package com.todos.application.controllers;

import com.todos.application.facades.impl.DefaultUserFacade;
import com.todos.application.facades.impl.exceptions.DuplicateUserException;
import com.todos.data.RegisterData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@EntityScan(basePackages = {"com.todos.model"})
@ComponentScan(basePackages = {"com.todos"})
@RunWith(SpringJUnit4ClassRunner.class)
public class RegisterControllerTest {

    @Mock
    private DefaultUserFacade userFacade;

    private MockMvc mockMvc;

    private RegisterData registerData;

    @Before
    public void setup() {
        RegisterController controller = new RegisterController();
        registerData = new RegisterData();

        userFacade = mock(DefaultUserFacade.class);
        controller.setUserFacade(userFacade);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(viewResolver).build();
    }

    /**
     * Test if '/register' url return proper view.
     */
    @Test
    public void showRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("registerData", hasProperty("login", isEmptyOrNullString())))
                .andExpect(model().attribute("registerData", hasProperty("email", isEmptyOrNullString())))
                .andExpect(model().attribute("registerData", hasProperty("password", isEmptyOrNullString())))
                .andExpect(view().name("register"));
    }

    /**
     * Test registration process. User data has some errors. Controller should add error parameter to model.
     */
    @Test
    public void processRegisterFormWrongLoginPassed() throws Exception {
        registerData.setLogin("");
        registerData.setEmail("email@test.com");
        registerData.setPassword("passw");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("registerData", registerData))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("registerData", hasProperty("login", is(""))))
                .andExpect(model().attribute("registerData", hasProperty("email", is("email@test.com"))))
                .andExpect(model().attribute("registerData", hasProperty("password", is("passw"))))
                .andExpect(model().attributeHasErrors("registerData"));

        verify(userFacade, never()).register(registerData); // registration will be never performed.
    }

    /**
     * Test registration process. User data has some errors. Controller should add error parameter to model.
     */
    @Test
    public void processRegisterFormWrongEmailPassed() throws Exception {
        registerData.setLogin("dummy");
        registerData.setEmail("");
        registerData.setPassword("passw");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("registerData", registerData))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("registerData", hasProperty("login", is("dummy"))))
                .andExpect(model().attribute("registerData", hasProperty("email", is(""))))
                .andExpect(model().attribute("registerData", hasProperty("password", is("passw"))))
                .andExpect(model().attributeHasErrors("registerData"));

        verify(userFacade, never()).register(registerData); // registration will be never performed.
    }

    /**
     * Test registration process. User data has some errors. Controller should add error parameter to model.
     */
    @Test
    public void processRegisterFormWrongPasswordPassed() throws Exception {
        registerData.setLogin("dummy");
        registerData.setEmail("email@test.com");
        registerData.setPassword("");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("registerData", registerData))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("registerData", hasProperty("login", is("dummy"))))
                .andExpect(model().attribute("registerData", hasProperty("email", is("email@test.com"))))
                .andExpect(model().attribute("registerData", hasProperty("password", is(""))))
                .andExpect(model().attributeHasErrors("registerData"));

        verify(userFacade, never()).register(registerData); // registration will be never performed.
    }

    /**
     * Test registration process. User data has properly value and registration should finish with success.
     */
    @Test
    public void processRegisterProperDataPassed() throws Exception {
        registerData.setLogin("dummy");
        registerData.setEmail("email@test.com");
        registerData.setPassword("passw");

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("registerData", registerData))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("registerData", hasProperty("login", is("dummy"))))
                .andExpect(model().attribute("registerData", hasProperty("email", is("email@test.com"))))
                .andExpect(model().attribute("registerData", hasProperty("password", is("passw"))));

        verify(userFacade, times(1)).register(registerData); // registration will be performed.
    }

    /**
     * Test registration process. User data has properly value and registration should finish with success.
     */
    @Test
    public void processRegisterProperDataDuplicatedUser() throws Exception {
        registerData.setLogin("dummy");
        registerData.setEmail("email@test.com");
        registerData.setPassword("passw");

        doThrow(new DuplicateUserException("Error")).when(userFacade).register(registerData);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("registerData", registerData))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("registerData", hasProperty("login", is("dummy"))))
                .andExpect(model().attribute("registerData", hasProperty("email", is("email@test.com"))))
                .andExpect(model().attribute("registerData", hasProperty("password", is("passw"))));

        verify(userFacade, times(1)).register(registerData); // registration will be performed once.
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}