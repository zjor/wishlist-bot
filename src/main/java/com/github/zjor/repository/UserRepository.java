package com.github.zjor.repository;

import com.github.zjor.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByExtId(String extId);

    default User ensure(String extId, String username, String firstName, String lastName) {
        var existing = findUserByExtId(extId);
        if (existing.isPresent()) {
            return existing.get();
        } else {
            var created = save(User.builder()
                    .extId(extId)
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build());
            return created;
        }
    }
}
