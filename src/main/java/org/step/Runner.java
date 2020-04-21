package org.step;

import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;
import org.step.repository.impl.UserRepositoryImpl;
import org.step.security.Role;

public class Runner {

    public static void main(String[] args) {
        UserRepository<User> userRepository = new UserRepositoryImpl();
        AuthoritiesRepository<User> authoritiesRepository = new UserRepositoryImpl();

        User user = new User("third", "third");

        User save = userRepository.save(user);
        save.setRole(Role.ROLE_USER);
        boolean isAuthoritiesSaved = authoritiesRepository.saveAuthorities(save);

        System.out.printf("Is authorities saved? %b", isAuthoritiesSaved);
    }
}
