package ru.nikitin.userservice.codemark.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String login;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "role")
    @Column(name = "role_name")
    @ElementCollection(targetClass = Role.class)
    private Set<Role> roles;

    public User() {
    }

    public User(String login, String name, String password, Role role, Role... roles) {
        this(login, name, password, EnumSet.of(role, roles));
    }

    public User(String login, String name, String password, Collection<Role> roles) {
        this.login = login;
        this.name = name;
        this.password = password;
        setRoles(roles);
    }

    public User(String login, String name, String password) {
        this(login, name, password, Role.EMPLOYEE);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
