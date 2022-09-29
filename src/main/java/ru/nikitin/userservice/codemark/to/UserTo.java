package ru.nikitin.userservice.codemark.to;

import org.springframework.data.util.Pair;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserTo {

    private String login;
    private String name;
    private String password;
    private Set<String> roles;

    public UserTo(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;

    }

    public UserTo(String login, String name, String password, Set<String> roles) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }


    public UserTo(User user, Set<String> strings) {
        this(user.getLogin(), user.getName(), user.getPassword(), strings);
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
            setRole = roles != null ? Collections.emptySet() :
                    roles.stream()
                            .map(RoleName::valueOf)
                            .map(role -> new Role(role.name(), user))
                            .collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Throws an exception when converting a String to a Enum RoleName");
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
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
}
