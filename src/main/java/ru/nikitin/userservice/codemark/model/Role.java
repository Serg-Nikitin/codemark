package ru.nikitin.userservice.codemark.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "role")
@IdClass(RolePK.class)
public class Role implements Persistable<RolePK> {

    @Transient
    private boolean isNew = true;

    @Id
    private String role_name;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_login", referencedColumnName = "login")})
    private User user;


    public Role() {
    }

    public Role(String role_name, User user) {
        this.role_name = role_name;
        this.user = user;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_name='" + role_name + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public RolePK getId() {
        return new RolePK(role_name, user);
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
