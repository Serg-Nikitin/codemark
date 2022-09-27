package ru.nikitin.userservice.codemark.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nikitin.userservice.codemark.model.User;
import ru.nikitin.userservice.codemark.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получать список пользователей из БД (без ролей)
     * */
    public List<User> getAll() {
        return userRepository.findAll(Sort.by("login"));
    }

    /**
     * Добавлять нового пользователя с ролями в БД
     * @param user
     * @return
     */
    public User create(User user) {
        return userRepository.save(user);
    }

    /**
     * Редактировать существующего пользователя в БД.
     * Если в запросе на редактирование передан массив ролей,
     * система должна обновить список ролей пользователя в БД
     * (новые привязки добавить, неактуальные привязки удалить).
     * @param login
     * @param user
     * @return
     */
    public User update(String login, User user) {
//        todo check user
        return userRepository.save(user);
    }

    /**
     * Получать конкретного пользователя (с его ролями) из БД
     * @param login
     * @return
     */
    public User getUser(String login){
        return userRepository.getUserByLogin(login);
    }


    /**
     * Удалять пользователя в БД
     * @param login
     * @return
     */
    public boolean deleteUser(String login){
        return userRepository.deleteByLogin(login);
    }


}
