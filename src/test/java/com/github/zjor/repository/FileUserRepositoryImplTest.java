package com.github.zjor.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUserRepositoryImplTest {

    private UserRepository repository;
    private File tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = File.createTempFile("users", ".json");
        var filename = tempFile.getAbsolutePath();
        log.info("Using file: {}", filename);
        repository = new FileUserRepositoryImpl(filename);
    }

    @After
    public void tearDown() {
        tempFile.delete();
    }

    @Test
    public void shouldCreateUser() {
        var user = repository.create("123", "alice.brown", "Alice", "Brown");
        Assert.notNull(user.getId(), "id should be generated");
    }

    @Test
    public void shouldCreateAndFindByExtId() {
        var user = repository.create("123", "alice.brown", "Alice", "Brown");
        var found = repository.findByExtId("123");
        Assert.isTrue(found.isPresent(), "user should be found");
        Assert.isTrue(found.get().getId().equals(user.getId()), "user should be the same as created");
    }

}