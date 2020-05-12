package org.step.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.step.model.User;
import org.step.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    private void postConstruct() {
        // Заполнение базы данных
    }

    @PreDestroy
    private void preDestroy() {
        // удаление всего с базы данных
    }

    @Override
    public User save(User user) {
//        entityManager.getTransaction().begin();
        entityManager.persist(user);
//        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public boolean delete(User user) {
        int update = entityManager.createQuery("delete from User u where u.id=:id")
                .setParameter("id", user.getId())
                .executeUpdate();
        return update != -1;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
//        TypedQuery<User> query = entityManager
//                .createQuery("select u from User u where u.username=:username", User.class);
//        query.setParameter("username", username);
//        User singleResult = query.getSingleResult();

        return entityManager.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }
}
