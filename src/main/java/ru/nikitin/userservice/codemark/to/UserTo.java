package ru.nikitin.userservice.codemark.to;

import ru.nikitin.userservice.codemark.model.User;

import java.util.Set;

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
}
