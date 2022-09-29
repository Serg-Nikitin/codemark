package ru.nikitin.userservice.codemark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nikitin.userservice.codemark.model.Role;
import ru.nikitin.userservice.codemark.to.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r JOIN FETCH r.user WHERE r.user.login=:login")
    Optional<Roles> getRolesWithUser(String login);
}
