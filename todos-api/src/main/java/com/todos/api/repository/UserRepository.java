package com.todos.api.repository;

import com.todos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Search for user with selected login in database.
     *
     * @param username user's login.
     * @return user with specified login.
     */
    User findUserByUsername(String username);

    /**
     * Search for user with selected email in database.
     *
     * @param email user's email.
     * @return user with specified email.
     */
    User findUserByEmail(String email);
}
