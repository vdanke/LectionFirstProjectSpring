package org.step.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MESSAGES")
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", allocationSize = 1, sequenceName = "message_seq")
//    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    // @Min @Max - числа
    @Size(min = 5, max = 1024, message = "")
    @Column(length = 1025)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
