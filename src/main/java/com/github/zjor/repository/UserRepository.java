package com.github.zjor.repository;

import com.github.zjor.domain.User;

import java.util.Optional;

public interface UserRepository {

    User create(String username);

    User create(int id, String username);

    Optional<User> findById(int id);

    default User ensure(int id, String username) {
        Optional<User> found = findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            return create(id, username);
        }
    }

}
