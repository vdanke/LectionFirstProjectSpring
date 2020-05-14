package org.step.test;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.DatabaseConfiguration;
import org.step.configuration.web.WebAppInitializer;
import org.step.configuration.web.WebMvcConfig;
import org.step.model.Message;
import org.step.model.User;
import org.step.repository.UserRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, WebMvcConfig.class, WebAppInitializer.class})
@WebAppConfiguration
//@ActiveProfiles(value = {"dev", "test"})
public class TestBeanConfigurationExisting {

    private UserRepository<User> userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void setup() {
    }

    @Test
    public void shouldDatabaseNotBeNull()  {
        Assertions.assertThat(userRepository).isNotNull();
    }

//    @Test
//    public void test() {
//        Optional<User> byId = userRepository.findById(1L);
//
//        Assert.assertTrue(byId.isPresent());
//
//        User user = byId.get();
//
//        List<Message> messageList = user.getMessageList();
//    }

    @Test
    @Transactional
    public void test1() {
//        User user = entityManager.find(User.class, 1L);
//        EntityGraph<?> message_list = entityManager.getEntityGraph("message_list");
//        Map<String, Object> properties = new HashMap<>();
//
//        properties.put("javax.persistence.fetchgraph", message_list);
//
//        User user = entityManager.find(User.class, 1L, properties);
//        User user = entityManager.createQuery("select u from User u where u.id=:id", User.class)
//                .setParameter("id", 1L)
//                .setHint("javax.persistence.fetchgraph", message_list)
//                .getSingleResult();
        User user = entityManager.find(User.class, 1L);

        user.setUsername("username");

        userRepository.save(user);
//        List<Message> messageList = user.getMessageList();
//
//        Message message = messageList.get(0);
//
//        System.out.println(message.getDescription());
    }

    @Autowired
    public void setUserRepository(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }
}
