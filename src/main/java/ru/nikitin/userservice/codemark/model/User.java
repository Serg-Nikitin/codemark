package ru.nikitin.userservice.codemark.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;
import ru.nikitin.userservice.codemark.to.UserTo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Persistable<String> {

    @Id
    private String login;

    private String name;

    private String password;

    @Transient
    private Boolean isNew = true;

    public User(String login, String name, String password) {
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public User(UserTo to) {
        this(to.getLogin(), to.getName(), to.getPassword());
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
