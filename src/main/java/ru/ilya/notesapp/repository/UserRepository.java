package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ilya.notesapp.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
