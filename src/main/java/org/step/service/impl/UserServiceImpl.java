package org.step.service.impl;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.security.Role;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class UserServiceImpl implements UserService<User>, AuthoritiesService<User> {

    private UserRepository<User> userRepository;
    private AuthoritiesRepository<User> authoritiesRepository;
    private Random random;

    public UserServiceImpl(UserRepository<User> userRepository,
                           AuthoritiesRepository<User> authoritiesRepository,
                           Random random) {
        this.userRepository = userRepository;
        this.random = random;
        this.authoritiesRepository = authoritiesRepository;
    }

    @Override
    public User login(User user) {
        Optional<User> login = userRepository.login(user);

        User userFromDB = login.orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean isPasswordEqual = userFromDB.getPassword().equals(user.getPassword());

        if (isPasswordEqual) {
            return userFromDB;
        } else {
            throw new IllegalArgumentException("Password is not correct");
        }
    }

    @Override
    public boolean save(User user, boolean isAdmin) {
        final int thousand = 1000;
        int i = random.nextInt(thousand);

        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        user.setPassword(user.getPassword());

        User afterSaving = userRepository.save(user);

        if (afterSaving.getId() != null && afterSaving.getId() != 0) {
            if (isAdmin) {
                afterSaving.setRole(Role.ROLE_ADMIN);
            } else {
                afterSaving.setRole(Role.ROLE_USER);
            }
            return authoritiesRepository.saveAuthorities(afterSaving);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User findById(Long id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    @Override
    public String getAuthority(Long id) {
        userRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("User with ID %d not found", id)));

        return authoritiesRepository.findAuthoritiesByUserId(id)
                .orElse("Your role is unknown");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        if (user.getId() != null) {
            return userRepository.update(user);
        } else {
            throw new RuntimeException("User ID is null");
        }
    }

    @Override
    public boolean saveAuthorities(User user) {
        return false;
    }
}
