package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class FileUserRepositoryImpl extends AbstractFileRepository<User> implements UserRepository {

    public FileUserRepositoryImpl(String filename) {
        super(User.class, filename);
    }

    @Override
    public User create(String extId, String username, String firstName, String lastName) {
        User u = User.builder()
                .id(IdGenerator.nextId())
                .extId(extId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        addItem(u);
        return u;
    }

    @Override
    public Optional<User> findByExtId(String extId) {
        for (User user : getItems()) {
            if (extId.equals(user.getExtId())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return getItems();
    }
}
