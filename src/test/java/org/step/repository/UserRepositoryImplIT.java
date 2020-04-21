package org.step.repository;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.step.model.User;
import org.step.repository.impl.UserRepositoryImpl;

import java.util.List;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryImplIT {

    private static UserRepository<User> userRepository;
    private static List<User> userList;
    private static User user;

    @BeforeClass
    public static void setup() {
        userRepository = new UserRepositoryImpl();
        user = new User("first", "first");
        userRepository.save(user);
        user.setId(1L);
        userList = userRepository.findAll();
    }

    @Test
    public void shouldReturnAllUsersFromDatabase() {
        List<User> all = userRepository.findAll();

        all.forEach(user -> {
            System.out.println(user.getId());
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
        });

        Assert.assertTrue(all.contains(user));
        Assert.assertEquals(userList.size(), all.size());
    }

    @Test
    public void shouldSaveUserToDatabase() {
        User user = new User("second", "second");

        User afterSaving = userRepository.save(user);

        List<User> all = userRepository.findAll();

        user.setId(2L);

        Assert.assertNotNull(afterSaving);
        Assert.assertEquals(userList.size() + 1, all.size());
        Assert.assertTrue(all.contains(user));
    }

    @AfterClass
    public static void afterClass() {
        User fromSaveTest = new User(2L, "second", "second");

        userRepository.delete(fromSaveTest);
        userRepository.delete(user);

        userList.remove(user);

        List<User> all = userRepository.findAll();

        Assert.assertEquals(userList.size(), all.size());
        Assert.assertFalse(all.contains(fromSaveTest));
    }
}
