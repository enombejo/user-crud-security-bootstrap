package com.example.user_crud_spring_boot.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

// Этот класс реализует интерфейс GrantedAuthority, в котором необходимо переопределить только один метод getAuthority() (возвращает имя роли).
// Имя роли должно соответствовать шаблону: «ROLE_ИМЯ», например, ROLE_USER.
@Entity
@Table
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user")
    private User user;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Role(String role, User user) {
        this.user = user;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role1 = (Role) o;
        return /*getId().equals(role1.getId()) &&*/
                getRole().equals(role1.getRole()) &&
                getUser().equals(role1.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*getId(),*/ getRole(), getUser());
    }
}
