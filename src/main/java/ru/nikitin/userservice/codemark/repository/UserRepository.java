package ru.nikitin.userservice.codemark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitin.userservice.codemark.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLogin(String login);

    boolean deleteByLogin(String login);
}
