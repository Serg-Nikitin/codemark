package ru.nikitin.userservice.codemark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.nikitin.userservice.codemark.model.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {

    @Modifying
    @Query("DELETE FROM User u WHERE u.login=?1")
    int deleteByLogin(String login);

    @Query("SELECT u.login FROM User u")
    Set<String> getAllLogin();
}
