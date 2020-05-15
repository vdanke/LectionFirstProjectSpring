package org.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.step.model.Message;

import java.util.List;

public interface MessageRepositorySpringData extends JpaRepository<Message, Long> {

    List<Message> findAllByUser_Id(Long id);
}
