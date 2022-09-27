package ru.nikitin.userservice.codemark.to;

import ru.nikitin.userservice.codemark.model.Role;

import java.util.Set;

public class UserTo {

    private String login;
    private String name;
    private String password;
    private Set<Role> roles;

}
