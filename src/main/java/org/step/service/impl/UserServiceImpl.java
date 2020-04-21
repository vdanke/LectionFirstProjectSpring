package org.step.service.impl;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.security.Role;
import org.step.service.AuthoritiesService;
import org.step.service.UserService;

import java.util.List;
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
    public boolean save(User user) {
        int i = random.nextInt(1000);
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        user.setPassword(user.getPassword() + "shifr" + i);

        User afterSaving = userRepository.save(user);

        if (afterSaving.getId() != null && afterSaving.getId() != 0) {
            afterSaving.setRole(Role.ROLE_USER);
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

    @Override
    public boolean saveAuthorities(User user) {
        return false;
    }
}
