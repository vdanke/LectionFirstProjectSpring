package org.step.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.service.impl.UserServiceImpl;

import java.util.Random;

public class UserServiceImplTestOld {

    @Mock
    private Random random;
    @Mock
    private UserRepository<User> userRepository;
    @InjectMocks
    private UserService<User> userService;

    @Before
    public void setup() {
        /*
        Mock метод заменяет реально существующий объект на его обертку
        В дальнейшем его использовании мы будем имитировать его работу
        userRepository = Mockito.mock(UserRepositoryImpl.class);
         */
        userService = new UserServiceImpl(userRepository, random);
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 200)
    public void shouldSetPasswordWithShifr() {
        User user = new User("eights", "fifth");

        /*
        Имитация работы метода save, при котором мы говорим, что пользватель
        успешно будет сохранен в базу данных, и вернется значение boolean - true
         */
        Mockito.when(userRepository.save(user)).thenReturn(true);

        boolean save = userService.save(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assert.assertTrue(save);
        Assert.assertTrue(user.getPassword().endsWith("shifr"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        boolean save = userService.save(null);

        Mockito.verify(userRepository, Mockito.never())
                .save(Mockito.any(User.class));
        Assert.assertFalse(save);
    }
}
