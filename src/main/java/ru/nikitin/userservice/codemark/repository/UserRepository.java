package ru.nikitin.userservice.codemark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.to.Roles;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getUserByLogin(String login);

    @Modifying
    @Query("DELETE FROM User u WHERE u.login=?1")
    int deleteByLogin(String login);

    @Query("SELECT r FROM Role r JOIN FETCH r.user WHERE r.user.login=:login")
    Roles getRolesWithUser(String login);

}
