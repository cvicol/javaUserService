package com.helkaerea.user.service.user;
import java.util.List;
public class UserRepositoryImpl implements UserRepository{
    private List<User> theUsers;

    @Override
    public void addNewUser(User user) {

    }

    @Override
    public void addNewUserWith(String userName, int userAge) {

    }

    public List<User> getAllUsers() {
        return theUsers;
    }

    public List<User> getAllUsersWithName(String Name) {
        return null;
    }
}
