package org.step.repository;

import org.junit.*;
import org.step.model.User;
import org.step.repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserRepositoryImplIT {

    private UserRepository<User> userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    public void shouldReturnAllUsersFromDatabase() {
        List<User> all = userRepository.findAll();

        all.forEach(user -> {
            System.out.println(user.getId());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
        });

        Assert.assertTrue(all.contains(new User(1L, "first", "first")));
        Assert.assertEquals(3, all.size());
    }

    @Test
    public void shouldSaveUserToDatabase() {
        User user = new User("fourth", "fourth");

        boolean isSaved = userRepository.save(user);
        final int sizeAfterSaving = 4;

        List<User> all = userRepository.findAll();

        user = new User(4L, "fourth", "fourth");

        Assert.assertTrue(isSaved);
        Assert.assertEquals(sizeAfterSaving, all.size());
        Assert.assertTrue(all.contains(user));
    }

    @After
    public void afterClass() {
        User user = new User(4L, "fourth", "fourth");

        userRepository.delete(user);

        List<User> all = userRepository.findAll();

        Assert.assertEquals(3, all.size());
        Assert.assertFalse(all.contains(user));
    }
}
