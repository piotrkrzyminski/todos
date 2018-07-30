package com.todos.api.services;

import com.todos.model.User;

public interface UserService {

    /**
     * Saves user to database. Used for registration process.
     * Register user only if data not exists in database.
     *
     * @param user user to save.
     */
    void save(User user);

    /**
     * Search for user in database using unique login.
     *
     * @param login login of user.
     * @return user with selected login or null.
     */
    User getUserByLogin(String login);

    /**
     * Search for user in database using unique email.
     *
     * @param email email of user.
     * @return user with selected email or null.
     */
    User getUserByEmail(String email);
}
