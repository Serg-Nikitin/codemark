package ru.nikitin.userservice.codemark.soap.validation;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
        "login",
        "name",
        "password",
        "roles"
})
public class UserSoap1 {

    @XmlElement(required = true)
    protected String login;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String password;
    @XmlElement(nillable = true)
    protected List<String> roles;


    public String getLogin() {
        return login;
    }


    public void setLogin(String value) {
        this.login = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String value) {
        this.password = value;
    }

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<String>();
        }
        return this.roles;
    }

}
