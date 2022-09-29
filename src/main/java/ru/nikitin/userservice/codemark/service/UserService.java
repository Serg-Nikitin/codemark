package ru.nikitin.userservice.codemark.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.repository.RoleRepository;
import ru.nikitin.userservice.codemark.repository.UserRepository;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.CustomLoginException;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final static String NOT_FOUND = "User with login = %s, not found";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Get a set of users from the database (without Roles)
     */
    public List<User> getAll() {
        return userRepository.findAll(Sort.by("login"));
    }


    /**
     * Get a specific user (with his roles) from the database
     *
     * @param login
     * @return
     */
    public UserTo getUserWithRole(String login) {
        return roleRepository
                .getRolesWithUser(login)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND, login)))
                .createUserTo();
    }

    /**
     * Delete a user in the database
     *
     * @param login
     * @return
     */
    @Transactional
    public boolean deleteUser(String login) {
        return userRepository.deleteByLogin(login) == 1;
    }


    /**
     * Add a new user with roles in the database
     *
     * @param userTo
     * @return
     */
    @Transactional
    public UserTo create(UserTo userTo) {
        Pair<User, Set<Role>> pair = userTo.getUserWithSetRole();
        User user = pair.getFirst();
        Set<Role> set = pair.getSecond();
        userRepository.save(user);

        if (set.isEmpty()) {
            set.add(new Role(RoleName.EMPLOYEE.name(), user));
        }

        roleRepository.saveAll(set);
        return user.getUserTo(set);
    }

    /**
     * Edit an existing user in the database.
     * If an array of roles is passed in the editing request,
     * the system must update the list of user roles in the database
     * (add new bindings, delete outdated bindings).
     *
     * @param login
     * @param userTo
     * @return
     */
    @Transactional
    public UserTo update(String login, UserTo userTo) {
        Pair<User, Set<Role>> pair = userTo.getUserWithSetRole();
        User user = pair.getFirst();
        if (!login.equals(user.getLogin())) {
            throw new CustomLoginException("This login must be equals user.login");
        }
        Set<Role> inPutSet = pair.getSecond();
        if (inPutSet.isEmpty()) {
            userRepository.save(user);
        } else {
            deleteUser(login);
            roleRepository.saveAll(inPutSet);
        }
        return user.getUserTo(inPutSet);
    }
}
