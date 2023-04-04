package com.github.zjor.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zjor.domain.User;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

// Singleton
public class FileUserRepositoryImpl implements UserRepository {

    private final String filename;

    public FileUserRepositoryImpl(String filename) {
        this.filename = filename;
    }

    @SneakyThrows
    private List<User> loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        Reader reader = new FileReader(filename);
        TypeReference<List<User>> typeReference = new TypeReference<>() {
        };
        return mapper.readValue(reader, typeReference);
    }

    @Override
    public User create(String username) {

        return null;
    }

    @Override
    public Optional<User> findById(int id) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
