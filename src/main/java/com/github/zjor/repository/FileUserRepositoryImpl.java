package com.github.zjor.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zjor.domain.User;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

// Singleton
public class FileUserRepositoryImpl implements UserRepository {

    private final ObjectMapper mapper = new ObjectMapper();
    private List<User> usersCache = new LinkedList<>();
    private boolean isDirty = true;

    private final String filename;

    public FileUserRepositoryImpl(String filename) {
        this.filename = filename;
    }

    private int nextId() {
        if (isDirty) {
            loadUsers();
        }

        int maxId = -1;
        for (User u: usersCache) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        return maxId + 1;
    }

    @SneakyThrows
    private void loadUsers() {
        try (Reader reader = new FileReader(filename)) {
            TypeReference<List<User>> typeReference = new TypeReference<>() {
            };
            usersCache = mapper.readValue(reader, typeReference);
        }
        isDirty = false;
    }

    @SneakyThrows
    private void storeUsers() {
        try (Writer writer = new FileWriter(filename)) {
            mapper.writeValue(writer, usersCache);
        }
        isDirty = false;
    }

    @Override
    public User create(String username) {
        User u = User.builder()
                .id(nextId())
                .username(username)
                .build();
        usersCache.add(u);
        isDirty = true;
        storeUsers();
        return u;
    }

    @Override
    public Optional<User> findById(int id) {
        if (isDirty) {
            loadUsers();
        }
        for (User user : usersCache) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
