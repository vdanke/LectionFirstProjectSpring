package org.step.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.service.impl.UserServiceImpl;

import java.util.List;

public class UserServiceImplTest {

    private UserRepository<User> userRepository;
    private UserService<User> userService;

    @Before
    public void setup() {
        userRepository = Mockito.mock(UserRepositoryImpl.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test(timeout = 100)
    public void shouldSetPasswordWithShifr() {
        User user = new User("fifth", "fifth");

        Mockito.when(userRepository.save(user)).thenReturn(true);

        boolean save = userService.save(user);

        Assert.assertTrue(save);
        Assert.assertTrue(user.getPassword().endsWith("shifr"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        boolean save = userService.save(null);

        Assert.assertFalse(save);
    }
}
