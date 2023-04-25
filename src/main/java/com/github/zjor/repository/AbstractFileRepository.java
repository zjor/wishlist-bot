package com.github.zjor.repository;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class AbstractFileRepository<T> {

    private final Class<T> targetClass;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename;
    private List<T> items = new LinkedList<>();
    private boolean isDirty = true;

    public AbstractFileRepository(Class<T> targetClass, String filename) {
        this.targetClass = targetClass;
        this.filename = filename;
    }

    @SneakyThrows
    protected void loadItems() {
        if (!isDirty) {
            return;
        }
        try (Reader reader = new FileReader(filename)) {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, targetClass);
            items = mapper.readValue(reader, type);
        }
        isDirty = false;
    }

    @SneakyThrows
    private void storeItems() {
        if (!isDirty) {
            return;
        }
        try (Writer writer = new FileWriter(filename)) {
            mapper.writeValue(writer, items);
        }
        isDirty = false;
    }

    protected List<T> getItems() {
        loadItems();
        return items;
    }

    protected void addItem(T item) {
        items.add(item);
        isDirty = true;
        storeItems();
    }

}
