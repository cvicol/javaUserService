package com.helkaerea.user.service.user;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;


public class UserRepositoryTest {
    UserRepositoryImpl repository ;
    @Before
    public void init() {
        repository = new UserRepositoryImpl();
    }
    @Test
    public void addingANewUserToTheRepositoryWillPersistIt() {
        User newUser = new User("Claudia", 42);
        repository.addNewUser(newUser);
        List<User> users = repository.getAllUsers();
        Assert.assertTrue("Thesize or repo is 1", users.size() == 1);
        Assert.assertTrue("the user matches", newUser.equals(users.get(0)));
    }

    @Test(expected = DuplicateUserException.class)
    public void adding2usersWithSameNameAndAgeWillThrowDuplicateUserException() {
        User newUser = new User("Claudia", 42);
        User newUser2 = new User("Claudia", 42);
        repository.addNewUser(newUser);
    }

    @Test
    public void addingMultipleUsersToRepositoryAndReturningAllUsersWithName() {
        String theName = "Claudia";
        User newUser = new User("Claudia", 42);
        User newUser2 = new User("Claudia", 32);
        User newUser3 = new User("Claudia", 26);
        User newUser4 = new User("Johan", 26);
        repository.addNewUser(newUser);
        repository.addNewUser(newUser2);
        repository.addNewUser(newUser3);
        repository.addNewUser(newUser4);
        List<User> users = repository.getAllUsersWithName(theName);
        Assert.assertTrue("Thesize or repo is 3", users.size() == 3);
        Assert.assertTrue("the user1 matches", newUser.equals(users.get(0)));
        Assert.assertTrue("the user2 matches", newUser2.equals(users.get(1)));
        Assert.assertTrue("the user3 matches", newUser3.equals(users.get(2)));
    }
}
