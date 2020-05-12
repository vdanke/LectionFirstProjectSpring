package org.step.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", allocationSize = 1, sequenceName = "message_seq")
    private Long id;

    // @Min @Max - числа
    @Size(min = 5, max = 1024, message = "")
    @Column(length = 1025)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
