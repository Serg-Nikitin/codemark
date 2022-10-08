package ru.nikitin.userservice.codemark.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserServiceTest {


    @Autowired
    UserService service;

    public static UserTo getAlex = new UserTo("al", "Alex", "A1", List.of(RoleName.ANALYST.name(), RoleName.EMPLOYEE.name(), RoleName.LEAD.name()));
    public static UserTo createUser = new UserTo("cr", "Created", "C5", List.of(RoleName.ADMIN.name()));
    public static UserTo createOnlyUser = new UserTo("cr", "Created", "C5");
    public static UserTo updateUser = new UserTo("al", "Updated", "U6", List.of(RoleName.EMPLOYEE.name()));
    public static UserTo updateOnlyUser = new UserTo("al", "Updated", "U6");


    public static List<UserTo> USERS = List.of(
            new UserTo("al", "Alex", "A1"),
            new UserTo("ru", "Ruslan", "R3"),
            new UserTo("ve", "Veronika", "V4"),
            new UserTo("yu", "Yuri", "Y2"));


    @Test
    void getAll() {
        List<UserTo> list = service.getAll();
        list.forEach(System.out::println);
        assertEquals(USERS, list);
    }

    @Test
    void getUserWithRole() {
        UserTo to = service.getUserWithRole("al");
        assertEquals(getAlex, to);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createWithRoles() {
        UserTo newUser = service.create(createUser);
        assertEquals(createUser, newUser);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createOnlyUser() {
        UserTo newUser = service.create(createOnlyUser);
        createOnlyUser.setRoles(List.of(RoleName.EMPLOYEE.name()));
        assertEquals(createOnlyUser, newUser);
    }


    @Test
    void createIfExists() {
        assertThrows(DataIntegrityViolationException.class, () -> service.create(updateUser));
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateHappyPathWithRoles() {
        UserTo up = service.update(updateUser);
        assertEquals(updateUser, up);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateOnlyUserHappyPath() {
        UserTo up = service.update(updateOnlyUser);
        assertEquals(updateOnlyUser, up);
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser() {
        service.deleteUser("al");
        assertThrows(NotFoundException.class, () -> service.getUserWithRole("al"));
    }
}