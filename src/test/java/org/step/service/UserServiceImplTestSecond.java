package org.step.service;

import org.assertj.core.api.Assertions;
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

public class UserServiceImplTestSecond {

    @Mock
    private Random random;
    @Mock
    private UserRepository<User> userRepository;
    @InjectMocks
    private UserService<User> userService;

    @Before
    public void setup() {
        /*
        private Random random;
        random = Mockito.mock(Random.class);
        userRepository = Mockito.init(UserRepositoryImpl.class);
        userService = new UserServiceImpl(userRepository);
         */
        userService = new UserServiceImpl(userRepository, random);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSaveUserToDatabase() {
        // Инициализируем тестовые данные
        User user = new User("second", "second");

        final int passwordInteger = 5;

        // Задаем поведение Mock объектов
        Mockito.when(random.nextInt(Mockito.anyInt()))
                .thenReturn(passwordInteger);
        Mockito.when(userRepository.save(user))
                .thenReturn(true);

        // Вызываем реальный метод
        boolean save = userService.save(user);

        // Проверяем сколько раз был вызыван метод у Mock объекта
        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
        Mockito.verify(random, Mockito.times(1))
                .nextInt(Mockito.anyInt());

        // Проверяем полученные данные в методе (тестируем логику)
        Assert.assertTrue(save);
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getPassword())
                .startsWith("sec")
                .endsWith("shifr5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        // Инициализируем тестовые данные
        final int passwordInteger = 5;

        // Задаем поведение Mock объектов
        Mockito.when(random.nextInt(Mockito.anyInt()))
                .thenReturn(passwordInteger);

        // Вызываем реальный метод
        boolean isSaved = userService.save(null);

        // Проверяем сколько раз был вызыван метод у Mock объекта
        Mockito.verifyZeroInteractions(userRepository);
        Mockito.verify(random, Mockito.times(1))
                .nextInt(Mockito.anyInt());

        // Проверяем полученные данные в методе (тестируем логику)
        Assertions.assertThat(isSaved).isFalse();
        Assertions.assertThatThrownBy(() -> {
            throw new IllegalArgumentException("User is null");
        }).hasMessage("User is null");
    }
}
