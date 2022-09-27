package ru.nikitin.userservice.codemark.data;

import ru.nikitin.userservice.codemark.MatcherFactory;
import ru.nikitin.userservice.codemark.model.User;

import java.util.List;

public class UserData {

    public static MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingEqualsComparator(User.class);

    public static List<User> USERS = List.of(
            new User("al", "Alex", "alex"),
            new User("ru", "Ruslan", "rus"),
            new User("yu", "Yuri", "yuri"));

    public static User createUser = new User("cr", "Created", "create");
    public static User updated = new User("al", "Updated", "update");
    public static User getUser = new User("al", "Alex", "alex");

}
