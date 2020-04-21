package org.step.repository;

import org.step.model.User;

public interface AuthoritiesRepository<T extends User> {

    boolean saveAuthorities(T t);
}
