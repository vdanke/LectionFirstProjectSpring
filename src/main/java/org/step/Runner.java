package org.step;

import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class Runner {

    public static void main(String[] args) {
        UserRepository<User> userRepository = new UserRepositoryImpl();

        User user = new User("third", "third");

//        boolean isSaved = userRepository.save(user);
//        boolean isDeleted = userRepository.delete(user);
//        List<User> users = userRepository.findAll();
        Optional<User> byId = userRepository.findById(2L);

        byId.ifPresent(u -> {
            System.out.printf("My username is: %s", u.getUsername() + "\n");
            System.out.printf("My password is: %s", u.getPassword() + "\n");
        });
//        users.forEach(u -> {
//            System.out.printf("My username is: %s", u.getUsername() + "\n");
//            System.out.printf("My password is: %s", u.getPassword() + "\n");
//        });

//        System.out.printf("Is user saved? %b", isSaved);
//        System.out.printf("Is user deleted: %b", isDeleted);
    }
}
