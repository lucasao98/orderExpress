package com.foody.delivery.database.repository;

import com.foody.delivery.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String email);
}
