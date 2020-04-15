package org.step.service.impl;

import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService<User> {

    private UserRepository<User> userRepository;

    public UserServiceImpl(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        user.setPassword(user.getPassword() + "shifr");

        return userRepository.save(user);
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return null;
    }
}
