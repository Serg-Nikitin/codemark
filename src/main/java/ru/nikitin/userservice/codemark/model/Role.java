package ru.nikitin.userservice.codemark.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    private static final int START_SEQ = 50000;

    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "rol_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private RoleName role_name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Role() {
    }

    public Role(Integer id, RoleName role_name, User user) {
        this.id = id;
        this.role_name = role_name;
        this.user = user;
    }

    public RoleName getRole_name() {
        return role_name;
    }

    public void setRole_name(RoleName role_name) {
        this.role_name = role_name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
