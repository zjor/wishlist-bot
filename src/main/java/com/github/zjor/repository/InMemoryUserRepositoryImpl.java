package com.github.zjor.repository;

import com.github.zjor.domain.User;
import com.github.zjor.util.IdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<String, User> storage = new HashMap<>();

    @Override
    public synchronized User create(String extId, String username, String firstName, String lastName) {
        User user = User.builder()
                .id(IdGenerator.nextId())
                .extId(extId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        storage.put(extId, user);
        return user;
    }


    @Override
    public Optional<User> findByExtId(String extId) {
        return Optional.ofNullable(storage.get(extId));
    }

    @Override
    public List<User> findAll() {
        return storage.values().stream().toList();
    }

}
