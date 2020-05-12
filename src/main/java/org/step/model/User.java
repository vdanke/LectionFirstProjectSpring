package org.step.model;

import org.springframework.security.core.GrantedAuthority;
import org.step.security.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
//    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ_DB")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 1024)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    /*
    @OneToOne
    @ManyToMany
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    private List<Message> messageList;

    public User() {
    }

    public User(Long id, String username) {
        this.username = username;
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
