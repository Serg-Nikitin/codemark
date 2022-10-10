package ru.nikitin.userservice.codemark.utill;

import org.springframework.data.util.Pair;
import ru.nikitin.userservice.codemark.UserXML;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.to.UserTo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {


    public static Pair<User, List<Role>> getUserWithSetRole(UserTo to) {
        User user = new User(to);
        return Pair.of(user, getSetRole(user, to.getRoles()));
    }

    public static UserTo getUserTo(User user, Collection<Role> set) {
        return new UserTo(user, getStrings(set));
    }


    public static List<String> getStrings(Collection<Role> roles) {
        return roles
                .stream()
                .map(Role::getRoleName)
                .sorted()
                .collect(Collectors.toList());
    }

    private static List<Role> getSetRole(User user, List<String> roles) {
        try {
            return roles == null || roles.isEmpty() ?
                    Collections.emptyList() :
                    roles.stream()
                            .map(String::toUpperCase)
                            .map(RoleName::valueOf)
                            .map(role -> new Role(role.name(), user))
                            .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role must be null or match a collection of role");
        }
    }
}
