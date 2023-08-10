package com.github.zjor.bot;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Responses {

    private static final String START_TEMPLATE = "start.tg.txt";
    private static final String CREATE_TEMPLATE = "create.tg.txt";
    private static final String CANCEL_TEMPLATE = "cancel.tg.txt";

    private static final Map<String, String> templates = new HashMap<>();

    static {
        templates.put(START_TEMPLATE, loadTemplate(START_TEMPLATE));
        templates.put(CREATE_TEMPLATE, loadTemplate(CREATE_TEMPLATE));
        templates.put(CANCEL_TEMPLATE, loadTemplate(CANCEL_TEMPLATE));
    }

    private static String loadTemplate(String name) {
        var in = Responses.class.getClassLoader().getResourceAsStream("templates/" + name);
        assert in != null : "Failed to load " + name;
        try {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + name, e);
        }
    }

    public static String start(String username) {
        return String.format(templates.get(START_TEMPLATE), username);
    }

    public static String create() {
        return templates.get(CREATE_TEMPLATE);
    }

    public static String cancel() {
        return templates.get(CANCEL_TEMPLATE);
    }

}
