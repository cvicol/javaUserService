package com.helkaerea.user.service.user;

import com.helkaerea.user.service.user.User;
import com.helkaerea.user.service.user.UserRepository;

/**
 * Basic user service to add a new user.
 * It will call the repository to store the user.
 */
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void add(User user) {
        userRepository.addNewUser(user);
    }

    public void addWithComponent(User user) {
        userRepository.addNewUserWith(user.getName(), user.getAge());
    }
}