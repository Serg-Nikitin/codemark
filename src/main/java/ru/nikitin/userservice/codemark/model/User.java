package ru.nikitin.userservice.codemark.model;

import org.springframework.data.domain.Persistable;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements Persistable<String> {

    @Id
    private String login;

    private String name;

    private String password;

    @Transient
    private Boolean isNew = true;

    public User() {
    }

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        var user = (User) o;
        return login.equals(user.login) && name.equals(user.name) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
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

    public UserTo getUserTo(Collection<Role> set) {
        return new UserTo(this, getStrings(set));
    }


    private static Set<String> getStrings(Collection<Role> setRoles) {
        return setRoles
                .stream()
                .map(Role::getRole_name)
                .collect(Collectors.toSet());
    }

    @Override
    public String getId() {
        return login;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setIsNew() {
        isNew = false;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
