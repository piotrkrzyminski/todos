package com.todos.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserData extends AbstractData {

    @NotNull
    @NotEmpty
    private Long id;

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
