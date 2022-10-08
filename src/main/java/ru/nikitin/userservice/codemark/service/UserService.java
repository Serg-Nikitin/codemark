package ru.nikitin.userservice.codemark.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.model.RoleName;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.repository.RoleRepository;
import ru.nikitin.userservice.codemark.repository.UserRepository;
import ru.nikitin.userservice.codemark.to.Roles;
import ru.nikitin.userservice.codemark.to.UserTo;
import ru.nikitin.userservice.codemark.utill.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.nikitin.userservice.codemark.utill.UserUtil.getUserTo;
import static ru.nikitin.userservice.codemark.utill.UserUtil.getUserWithSetRole;

@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "logins")
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
    public List<UserTo> getAll() {
        return userRepository
                .findAll(Sort.by("login"))
                .stream()
                .map(UserTo::new)
                .collect(Collectors.toList());
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
    @CacheEvict(cacheNames = "logins", allEntries = true)
    public boolean deleteUser(String login) {
        return userRepository.deleteByLogin(login) == 1;
    }


    /**
     * Add a new user with roles in the database
     *
     * @param to
     * @return
     */
    @Transactional
    @CacheEvict(cacheNames = "logins", allEntries = true)
    public UserTo create(UserTo to) {
        Assert.notNull(to, "When create user, userTo must not be null");
        var pair = getUserWithSetRole(to);
        var user = pair.getFirst();
        user.setIsNew(checkLogin(to.getLogin()));
        var set = pair.getSecond().isEmpty() ?
                Set.of(new Role(RoleName.EMPLOYEE.name(), user)) : pair.getSecond();

        var streamable = Streamable.of(roleRepository.saveAll(set));
        return new Roles(streamable).createUserTo();
    }

    /**
     * Edit an existing user in the database.
     * If an array of roles is passed in the editing request,
     * the system must update the list of user roles in the database
     * (add new bindings, delete outdated bindings).
     *
     * @param to
     * @return
     */
    @Transactional
    public UserTo update(UserTo to) {
        Assert.notNull(to, "When update user, userTo must not be null");
        var pair = getUserWithSetRole(to);
        var user = pair.getFirst();
        user.setIsNew(checkLogin(to.getLogin()));
        var set = pair.getSecond();

        return set.isEmpty() ? updateUser(user, set) : updateWithRole(user, set);
    }

    @Cacheable("logins")
    public Set<String> getLogins() {
        return userRepository.getAllLogin();
    }

    private Boolean checkLogin(String login) {
        return !getLogins().contains(login);
    }

    private UserTo updateUser(User user, Collection<Role> set) {
        return getUserTo(userRepository.save(user), set);
    }

    private UserTo updateWithRole(User user, Collection<Role> set) {
        deleteUser(user.getLogin());
        var streamable = Streamable.of(roleRepository.saveAll(set));
        return new Roles(streamable).createUserTo();
    }
}
