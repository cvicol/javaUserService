package com.helkaerea.user.service.user;
import java.util.ArrayList;
import java.util.List;
public class UserRepositoryImpl implements UserRepository{
    private List<User> theUsers ;

    @Override
    public void addNewUser(User user) {

    }

    @Override
    public void addNewUserWith(String userName, int userAge) {

    }

    public List<User> getAllUsers() {
        return theUsers;
    }

    public List<User> getAllUsersWithName(String name) {
        return null;
    }

    //Q: Implement a method that allows you to get all the users over a certain age
    //Q: Implement a method that removes a User from the repository - based on name.
    //why did I use List - as the type of the list of users, and not an implementation of List?
}
