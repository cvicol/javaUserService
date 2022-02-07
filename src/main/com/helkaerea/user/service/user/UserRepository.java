package com.helkaerea.user.service.user;
import com.helkaerea.user.service.user.User;
/**
 * We create a simple interface.
 * The interface is called UserRepository and takes care of storing
 * new users in database.
 * 
 * A real implementation is not needed for the examples.
 */
public interface UserRepository {
    void addNewUser(User user);
    void addNewUserWith(String userName, int userAge);
}