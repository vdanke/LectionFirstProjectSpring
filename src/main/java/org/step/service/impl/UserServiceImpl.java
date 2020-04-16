package org.step.service.impl;

import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.service.UserService;

import java.util.List;
import java.util.Random;

public class UserServiceImpl implements UserService<User> {

    private UserRepository<User> userRepository;
    private Random random;

    public UserServiceImpl(UserRepository<User> userRepository, Random random) {
        this.userRepository = userRepository;
        this.random = random;
    }

    @Override
    public boolean save(User user) {
        int i = random.nextInt(1000);
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        user.setPassword(user.getPassword() + "shifr" + i);

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
