package ru.nikitin.userservice.codemark.data;

import ru.nikitin.userservice.codemark.MatcherFactory;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.to.UserTo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserData {

    public static MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingEqualsComparator(User.class);
    public static MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingEqualsComparator(UserTo.class);


    public static List<User> USERS = List.of(
            new User("al", "Alex", "alex"),
            new User("ru", "Ruslan", "rus"),
            new User("yu", "Yuri", "yuri"));

    public static UserTo createUserTo = new UserTo("cr", "Created", "create", Set.of(RoleName.EMPLOYEE.name(), RoleName.DEVELOPER.name()));
    public static UserTo updatedTo = new UserTo("al", "Updated", "update", Set.of(RoleName.ADMIN.name(), RoleName.DEVELOPER.name()));

    public static User createUser = new User("cr", "Created", "create");
    public static User updated = new User("al", "Updated", "update");
    public static User getAlex = new User("al", "Alex", "alex");
    public static Collection<Role> alexRoles = Set.of(RoleName.LEAD, RoleName.ANALYST, RoleName.EMPLOYEE)
            .stream()
            .map(roleName -> new Role(roleName.name(), getAlex))
            .collect(Collectors.toSet());

    public static UserTo getUserToFromAlexRoleEmployee() {
        return new UserTo(
                getAlex.getLogin(),
                "Duplicate",
                "duple",
                Set.of(RoleName.EMPLOYEE.name())
        );
    }

}
