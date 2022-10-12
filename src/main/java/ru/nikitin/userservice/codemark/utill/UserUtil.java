package ru.nikitin.userservice.codemark.utill;

import org.springframework.data.util.Pair;
import ru.nikitin.userservice.codemark.*;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.to.UserTo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.nikitin.userservice.codemark.model.RoleName.set;

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
            return checkRoles(roles) ?
                    Collections.emptyList() :
                    roles.stream()
                            .map(String::toUpperCase)
                            .map(RoleName::valueOf)
                            .map(role -> new Role(role.name(), user))
                            .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role must be match a collection of role");
        }
    }

    private static boolean checkRoles(List<String> roles) {
        boolean rolesIsNull = roles == null;
        boolean rolesIsEmpty = roles.isEmpty();
        boolean rolesSizeOne = roles.size() == 1;
        boolean rolesNotContainsSet = !set().contains(roles.get(0));
        return rolesIsNull || rolesIsEmpty || rolesSizeOne && rolesNotContainsSet;
    }

    public static UserWithRoleXsd convertToUserRoleXsd(UserTo to) {
        var userWithRoleXsd = (UserWithRoleXsd) getBaseUser(to, new UserWithRoleXsd());
        userWithRoleXsd.setListRole(new ListRole());
        userWithRoleXsd.getListRole().getRole().addAll(to.getRoles());
        return userWithRoleXsd;
    }

    public static UserXsd convertUserXsd(UserTo to) {
        return (UserXsd) getBaseUser(to, new UserXsd());
    }

    private static BaseUser getBaseUser(UserTo to, BaseUser xsd) {
        xsd.setLogin(to.getLogin());
        xsd.setName(to.getName());
        xsd.setPassword(to.getPassword());
        return xsd;
    }


    public static GetUserResponse convertUserToGetUserResponse(UserTo to) {
        var response = new GetUserResponse();
        response.setUserWithRoleXsd(convertToUserRoleXsd(to));
        return response;
    }


    public static UserTo convertXsdWithRoleToUser(UserWithRoleXsd xsd) {
        return new UserTo(xsd.getLogin(), xsd.getName(), xsd.getPassword(), xsd.getListRole().getRole());
    }

    public static UserTo convertXsdToUser(UserXsd xsd) {
        return new UserTo(xsd.getLogin(), xsd.getName(), xsd.getPassword());
    }
}
