package org.step.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.model.Message;
import org.step.repository.MessageRepository;
import org.step.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService<Message> {

    private final MessageRepository<Message> messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository<Message> messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public boolean delete(Message message) {
        return messageRepository.delete(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
