package ru.nikitin.userservice.codemark.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikitin.userservice.codemark.UserXML;
import ru.nikitin.userservice.codemark.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTo {

    @NotBlank(message = "it should not be empty")
    protected String login;

    @NotBlank(message = "it should not be empty")
    protected String name;

    @NotBlank(message = "it should not be empty")
    @Pattern(regexp = "((?=.*\\d)(?=.*[A-Z]).{2,})", message = "must contain an uppercase letter and a number")
    protected String password;

    @NotNull
    protected List<String> roles;


    public UserTo(User user) {
        this(user.getLogin(), user.getName(), user.getPassword());
    }

    public UserTo(String login, String name, String password) {
        this(login, name, password, Collections.emptyList());
    }

    public UserTo(User user, List<String> strings) {
        this(user.getLogin(), user.getName(), user.getPassword(), strings);
    }

    public UserXML convertXml() {
        var userXml = new UserXML();
        userXml.setLogin(this.getLogin());
        userXml.setName(this.getName());
        userXml.setPassword(this.getPassword());
        return userXml;
    }
}

