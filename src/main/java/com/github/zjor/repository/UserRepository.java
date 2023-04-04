package com.github.zjor.repository;

import com.github.zjor.domain.User;

import java.util.Optional;

public interface UserRepository {

    User create(String username);

    Optional<User> findById(int id);

}
