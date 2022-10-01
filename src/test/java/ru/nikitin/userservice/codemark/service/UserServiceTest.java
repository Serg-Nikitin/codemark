package ru.nikitin.userservice.codemark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nikitin.userservice.codemark.data.UserData.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;


    @Test
    void getAll() {
        USER_TO_MATCHER.assertMatch(USERS, service.getAll());
    }

    @Test
    void getUserWithRole() {
        UserTo userTo = service.getUserWithRole(getAlex.getLogin());
        UserTo userToAlex = getAlex.getUserTo(alexRoles);
        USER_TO_MATCHER.assertMatch(userToAlex,userTo);

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createHappyPathLotOfRoles() {
        UserTo newUser = service.create(createUserTo);
        USER_TO_MATCHER.assertMatch(newUser, createUserTo);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createHappyPathWithoutRole() {
        UserTo created = new UserTo(createUser);
        UserTo newUser = service.create(created);
        created.setRoles(List.of(RoleName.EMPLOYEE.name()));
        USER_TO_MATCHER.assertMatch(newUser, created);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateHappyPathWithoutRole() {
        UserTo update = new UserTo(updated);
        UserTo upUser = service.update(update.getLogin(), update);

        USER_TO_MATCHER.assertMatch(upUser, update);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateHappyPathLotOfRoles() {
        UserTo update = new UserTo(updated, List.of(RoleName.DEVELOPER.name(), RoleName.OPERATOR.name()));
        UserTo upUser = service.update(update.getLogin(), update);
        USER_TO_MATCHER.assertMatch(upUser, update);
    }


    @Test
    void createDuplicate() {
        assertThrows(DataAccessException.class, () -> service.create(getUserToFromAlexRoleEmployee()));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        service.deleteUser("al");
        assertThrows(NotFoundException.class, () -> service.getUser("al"));
    }
}