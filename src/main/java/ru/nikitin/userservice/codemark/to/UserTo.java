package ru.nikitin.userservice.codemark.to;

import org.springframework.data.util.Pair;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class UserTo extends ru.nikitin.userservice.codemark.User {

    public UserTo() {
    }

    public UserTo(User user) {
        this(user.getLogin(), user.getName(), user.getPassword());
    }

    public UserTo(String login, String name, String password) {
        this(login, name, password, Collections.emptyList());
    }

    public UserTo(User user, List<String> strings) {
        this(user.getLogin(), user.getName(), user.getPassword(), strings);
    }

    public UserTo(String login, String name, String password, List<String> roles) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    public Pair<User, Set<Role>> getUserWithSetRole() {
        User user = new User(login, name, password);
        Set<Role> setRole;
        try {
            setRole = roles.isEmpty() ? Collections.emptySet() :
                    roles.stream()
                            .map(String::toUpperCase)
                            .map(RoleName::valueOf)
                            .map(role -> new Role(role.name(), user))
                            .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Throws an exception when converting a String to a Enum RoleName getUserWithSetRole");
        }
        return Pair.of(user, setRole);
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTo)) return false;
        UserTo userTo = (UserTo) o;
        return login.equals(userTo.login) && name.equals(userTo.name) && password.equals(userTo.password) && roles.equals(userTo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, password, roles);
    }

    public static UserTo getToFromRequest(ru.nikitin.userservice.codemark.User user) {
        return new UserTo(
                user.getLogin(),
                user.getName(),
                user.getPassword(),
                user.getRoles());
    }
}
