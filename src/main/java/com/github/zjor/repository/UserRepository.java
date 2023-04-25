package com.github.zjor.repository;

import com.github.zjor.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User create(String extId, String username, String firstName, String lastName);

    Optional<User> findByExtId(String extId);

    default User ensure(String extId, String username, String firstName, String lastName) {

        Optional<User> found = findByExtId(extId);
        if (found.isPresent()) {
            return found.get();
        } else {
            return create(extId, username, firstName, lastName);
        }
    }

    List<User> findAll();
}
