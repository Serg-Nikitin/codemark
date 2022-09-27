package ru.nikitin.userservice.codemark.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nikitin.userservice.codemark.data.UserData.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;


    @BeforeEach
    void init() {
    }

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(USERS, service.getAll());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void create() {
        User newUser = service.create(createUser);
        USER_MATCHER.assertMatch(newUser, createUser);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void update() {
        service.update("al", updated);
        User actual = service.getUser("al");
        USER_MATCHER.assertMatch(actual, updated);
    }

    @Test
    void getUser() {
        User actual = service.getUser("al");
        USER_MATCHER.assertMatch(actual, getUser);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        service.deleteUser("al");
        assertThrows(NotFoundException.class, () -> service.getUser("al"));
    }
}