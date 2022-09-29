package ru.nikitin.userservice.codemark.model;

import java.io.Serializable;
import java.util.Objects;

public class RolePK implements Serializable {

    private String role_name;

    private User user;

    public RolePK() {
    }

    public RolePK(String role_name, User user) {
        this.role_name = role_name;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePK)) return false;
        RolePK rolePK = (RolePK) o;
        return role_name.equals(rolePK.role_name) && user.equals(rolePK.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_name, user);
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
}
