package com.github.zjor.repository;

import com.github.zjor.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Integer, User> storage = new HashMap<>();
    private int idGenerator = 0;

    @Override
    public synchronized User create(String username) {
        User user = User.builder()
                .id(idGenerator++)
                .username(username)
                .build();
        storage.put(user.getId(), user);
        return user;
    }

    @Override
    public User create(int id, String username) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }


}
